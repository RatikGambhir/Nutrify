package com.nutrify.lib

import kotlin.text.isNotBlank

class PromptFactory {
    companion object {
        private val promptMock: Map<String, String?> by lazy {
            mapOf(
                "registerUserMetadata" to loadPrompt("/prompts/prompt.md"),
                "Test" to loadPrompt("/prompts/prompt-pamphlet.md")
            )
        }
        fun getPrompt(name: String): String? {
            return promptMock[name]?.takeIf { it.isNotBlank() }
        }
        private fun loadPrompt(path: String): String? {
            try {
                return object{}.javaClass.getResource(
                    path
                )?.readText()
            } catch (e: Exception) {
                return null
            }
        }
    }
    private val prompts: Map<String, String?> by lazy {
        mapOf(
            "registerUserMetadata" to loadPrompt("/prompts/prompt.md"),
            "Test" to loadPrompt("/prompts/prompt-pamphlet.md")
        )
    }

    private fun loadPrompt(path: String): String? {
        try {
            return object{}.javaClass.getResource(
                path
            )?.readText()
        } catch (e: Exception) {
            return null
        }
    }

     fun get(name: String): String? =
        prompts[name]?.takeIf { it.isNotBlank() } ?: throw IllegalArgumentException("Prompt '$name' is blank or missing")
}