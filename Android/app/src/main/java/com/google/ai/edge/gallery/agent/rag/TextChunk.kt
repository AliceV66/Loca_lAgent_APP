package com.google.ai.edge.gallery.agent.rag

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.annotation.IndexType

@Entity
data class TextChunk(
    @Id var id: Long = 0,
    val sourceFileName: String,
    val chunkText: String,

    // ¡Nuestra nueva propiedad!
    // HNSW es el algoritmo para la búsqueda de vecinos más cercanos.
    // ObjectBox se encargará de toda la complejidad por nosotros.
    @Index(type = IndexType.HNSW)
    var vector: FloatArray? = null
) {
    // Sobrescribimos equals y hashCode para que ObjectBox pueda manejar
    // correctamente los arrays de floats.
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TextChunk

        if (id != other.id) return false
        if (sourceFileName != other.sourceFileName) return false
        if (chunkText != other.chunkText) return false
        if (vector != null) {
            if (other.vector == null) return false
            if (!vector.contentEquals(other.vector)) return false
        } else if (other.vector != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + sourceFileName.hashCode()
        result = 31 * result + chunkText.hashCode()
        result = 31 * result + (vector?.contentHashCode() ?: 0)
        return result
    }
}