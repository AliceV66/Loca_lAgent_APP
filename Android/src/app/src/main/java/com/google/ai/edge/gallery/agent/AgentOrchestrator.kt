package com.google.ai.edge.gallery.agent

import com.google.ai.edge.gallery.agent.rag.RagManager
import com.google.ai.edge.gallery.data.Model
import com.google.ai.edge.gallery.ui.llmchat.LlmChatModelHelper
import kotlinx.coroutines.runBlocking

object AgentOrchestrator {

    fun runChatFlow(
        model: Model,
        prompt: String,
        resultListener: (partialResult: String, done: Boolean) -> Unit,
        cleanUpListener: () -> Unit
    ) {
        // Ejecutamos la búsqueda en un contexto de corrutina
        runBlocking {
            // 1. Buscamos chunks de contexto relevantes para el prompt
            val relevantChunks = RagManager.searchRelevantChunks(prompt)

            // 2. Construimos un contexto enriquecido
            val contextText = if (relevantChunks.isNotEmpty()) {
                "Basado en el siguiente contexto:\n" +
                relevantChunks.joinToString("\n---\n") { it.chunkText } +
                "\n\nResponde la siguiente pregunta: "
            } else {
                ""
            }
            
            val finalPrompt = contextText + prompt

            // 3. Enviamos el prompt enriquecido al modelo
            LlmChatModelHelper.runInference(
                model = model,
                input = finalPrompt,
                resultListener = resultListener,
                cleanUpListener = cleanUpListener
            )
        }
    }
}