package com.nutrify.lib

class PromptFactory {

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
        prompts[name]?.takeIf { it.isNotBlank() }
}