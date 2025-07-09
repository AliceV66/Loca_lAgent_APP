# Google AI Edge Gallery ✨

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/google-ai-edge/gallery)](https://github.com/google-ai-edge/gallery/releases)

Local Agent: Tu Compañero de IA Privado y Portable
Este proyecto es un fork de la aplicación experimental Google AI Edge Gallery, con una nueva misión: crear un agente de inteligencia artificial verdaderamente personal, privado y portable que se ejecuta completamente en tu dispositivo.

Nuestra visión es transformar esta aplicación en un compañero digital. Su objetivo principal no es solo responder preguntas, sino ayudarte a ser la mejor versión de ti mismo. Aprenderá de ti, te ayudará a organizar tus ideas, a acceder a tu propio conocimiento y a interactuar con el mundo digital de una manera más inteligente y segura.

✨ Filosofía y Principios
Privacidad Absoluta: Todos tus datos y conversaciones se procesan y almacenan localmente en tu dispositivo. Nada se envía a servidores de terceros sin tu permiso explícito.

Propiedad del Usuario: Tu contexto, tu conocimiento, tu "alma" digital, te pertenece. Estamos implementando un sistema de "SOUL" portable, que te permitirá hacer copias de seguridad y migrar tu agente a otros dispositivos o aplicaciones en el futuro.

Inteligencia Aumentada: Mediante el uso de Retrieval-Augmented Generation (RAG), el agente puede usar los archivos y documentos que tú le proporciones como su base de conocimiento, ofreciendo respuestas precisas y contextualizadas.

Extensibilidad y Futuro: Construido sobre una arquitectura modular (inspirada en Google AI Genkit) y protocolos de comunicación de agentes (A2A, ACP), el proyecto está diseñado para crecer y adaptarse al futuro de la inteligencia artificial.

🚀 Características Principales
🤖 Inferencia 100% Local: Conversa con modelos de lenguaje de última generación sin necesidad de conexión a internet.

🧠 Memoria Persistente: El agente recuerda tus conversaciones y aprende de tus interacciones.

📄 Contexto desde Archivos (RAG): Dale al agente tus documentos, notas o cualquier archivo de texto para que los utilice como base de conocimiento.

🛠️ Uso de Herramientas (Próximamente):

Acceso a Internet para búsquedas en tiempo real.

Intermediario para APIs de modelos en la nube (OpenAI, Anthropic, etc.), bajo tu control.

🕊️ "SOUL" Portable: Exporta el "alma" completa de tu agente (memoria, conocimiento y configuraciones) a un único archivo para copias de seguridad y migración.

🛠️ Pila Tecnológica
Lenguaje: Kotlin

UI: Jetpack Compose

Inferencia Local: MediaPipe LLM Inference API

Orquestación de Agente: Arquitectura inspirada en Google AI Genkit

Gestión de Contexto: Model-Context Protocol (MCP)

Base de Conocimiento: Retrieval-Augmented Generation (RAG) con una base de datos vectorial local.

Comunicación: Preparado para A2A Protocol y Agent Communication Protocol (ACP).

🏁 Estado del Proyecto
Actualmente, nos encontramos en la fase inicial de refactorización, simplificando la base de código original para centrarnos en una única interfaz de chat y sentando las bases de la nueva arquitectura.

🤝 Contribuciones
Aunque estamos sentando las bases, la visión es que este sea un proyecto comunitario. Las directrices para contribuciones se definirán en una etapa más avanzada del desarrollo.