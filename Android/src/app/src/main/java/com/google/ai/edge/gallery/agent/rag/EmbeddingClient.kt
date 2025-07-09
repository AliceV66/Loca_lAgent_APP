package com.google.ai.edge.gallery.agent.rag

import android.content.Context
import android.util.Log
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.text.textembedder.TextEmbedder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicReference

object EmbeddingClient {
    private const val TAG = "EmbeddingClient"
    private const val MODEL_NAME = "universal_sentence_encoder.tflite"

    // Usamos AtomicReference para una inicialización segura en un entorno multihilo.
    private val textEmbedder = AtomicReference<TextEmbedder?>(null)

    fun initialize(context: Context) {
        // La inicialización es pesada, la hacemos en un hilo de fondo.
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (textEmbedder.get() == null) {
                    val options = TextEmbedder.TextEmbedderOptions.builder()
                        .setBaseOptions(BaseOptions.builder().setModelAssetPath(MODEL_NAME).build())
                        .build()
                    val embedder = TextEmbedder.createFromOptions(context, options)
                    textEmbedder.set(embedder)
                    Log.d(TAG, "TextEmbedder inicializado correctamente.")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al inicializar TextEmbedder: ${e.message}")
            }
        }
    }

    suspend fun generate(text: String): FloatArray = withContext(Dispatchers.Default) {
        val embedder = textEmbedder.get()
        if (embedder == null) {
            Log.e(TAG, "TextEmbedder no está inicializado.")
            return@withContext FloatArray(0) // Devuelve un array vacío si hay error
        }

        try {
            val embeddingResult = embedder.embed(text)
            // Tomamos el primer embedding, que corresponde al texto completo.
            return@withContext embeddingResult.embeddingResult().embeddings()[0].floatVector()
        } catch (e: Exception) {
            Log.e(TAG, "Error al generar embedding: ${e.message}")
            return@withContext FloatArray(0)
        }
    }
}