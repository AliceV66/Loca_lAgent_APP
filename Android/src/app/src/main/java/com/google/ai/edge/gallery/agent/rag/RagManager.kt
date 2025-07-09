package com.google.ai.edge.gallery.agent.rag

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.google.ai.edge.gallery.GalleryApplication
import io.objectbox.kotlin.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

object RagManager {

    // Modificamos esta función para que sea 'suspend'
    suspend fun addTextFile(context: Context, fileUri: Uri) = withContext(Dispatchers.IO) {
        val application = context.applicationContext as GalleryApplication
        val textChunkBox = application.boxStore.boxFor(TextChunk::class.java)

        val fileName = getFileNameFromUri(context, fileUri)
        val textContent = readTextFromUri(context, fileUri)
        val chunks = textContent.split("\n\n").filter { it.isNotBlank() }

        val textChunkObjects = chunks.map { chunk ->
            // 1. Generamos el embedding para cada chunk
            val vector = EmbeddingClient.generate(chunk)
            // 2. Creamos el objeto TextChunk con su vector
            TextChunk(sourceFileName = fileName, chunkText = chunk, vector = vector)
        }

        // 3. Guardamos los objetos en la base de datos
        textChunkBox.put(textChunkObjects)
    }

    // ¡Nuestra nueva función de búsqueda!
    suspend fun searchRelevantChunks(query: String, maxResults: Int = 3): List<TextChunk> = withContext(Dispatchers.IO) {
        // 1. Obtenemos el BoxStore y generamos el embedding para la pregunta
        val application = GalleryApplication::class.java.cast(GalleryApplication.instance)!!
        val textChunkBox = application.boxStore.boxFor(TextChunk::class.java)
        val queryVector = EmbeddingClient.generate(query)
        
        // 2. Construimos la query de ObjectBox para encontrar los vecinos más cercanos
        val queryResult = textChunkBox.query {
            nearestNeighbors(TextChunk_.vector, queryVector, maxResults)
        }.findWithScores() // findWithScores nos da los resultados con su "distancia"

        // 3. Devolvemos los TextChunk encontrados
        return@withContext queryResult.map { it.entity }
    }

    private fun readTextFromUri(context: Context, uri: Uri): String {
        val stringBuilder = StringBuilder()
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append('\n')
                }
            }
        }
        return stringBuilder.toString()
    }
    
    private fun getFileNameFromUri(context: Context, uri: Uri): String {
        var fileName = "document.txt" // Valor por defecto
        if (uri.scheme == "content") {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        fileName = cursor.getString(nameIndex)
                    }
                }
            }
        } else if (uri.scheme == "file") {
            fileName = uri.lastPathSegment ?: fileName
        }
        return fileName
    }
}