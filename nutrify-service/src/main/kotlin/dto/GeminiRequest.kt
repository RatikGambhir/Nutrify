package com.nutrify.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequest(
    val contents: List<GeminiContent>,
    val generationConfig: GenerationConfig? = null,
    val systemInstruction: SystemInstruction? = null
) {
    companion object {
        fun fromPrompt(prompt: String, config: GenerationConfig? = null, systemInstruction: String? = null): GeminiRequest {
            return GeminiRequest(
                contents = listOf(
                    GeminiContent(
                        parts = listOf(GeminiPart(text = prompt))
                    )
                ),
                generationConfig = config,
                systemInstruction = systemInstruction?.let { SystemInstruction(parts = listOf(GeminiPart(text = it))) }
            )
        }
    }
}

@Serializable
data class GenerationConfig(
    val temperature: Double? = null,
    val topK: Int? = null,
    val topP: Double? = null,
    val maxOutputTokens: Int? = null,
    val responseMimeType: String? = null,
    val responseSchema: ResponseSchema? = null
)

@Serializable
data class ResponseSchema(
    val type: String,
    val properties: Map<String, SchemaProperty>? = null,
    val required: List<String>? = null
)

@Serializable
data class SchemaProperty(
    val type: String,
    val description: String? = null
)

@Serializable
data class SystemInstruction(
    val parts: List<GeminiPart>
)

@Serializable
data class GeminiContent(
    val parts: List<GeminiPart>
)

@Serializable
data class GeminiPart(
    val text: String
)

@Serializable
data class GeminiResponse(
    val candidates: List<GeminiCandidate>
)

@Serializable
data class GeminiCandidate(
    val content: GeminiContent
)
