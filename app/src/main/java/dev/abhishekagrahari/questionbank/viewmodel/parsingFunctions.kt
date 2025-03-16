package dev.abhishekagrahari.questionbank.viewmodel

import androidx.compose.ui.semantics.SemanticsProperties.Text
import dev.abhishekagrahari.questionbank.model.Question
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable


fun parseDifficultyDistribution(json: String?): Map<String, Int>? {
    return json?.let {
        try {
            Json.decodeFromString<Map<String, Int>>(it)
        } catch (e: Exception) {
            null
        }
    }
}

fun parseQuestions(json: String?): List<Question>? {
    return json?.let {
        try {
            Json.decodeFromString<List<Question>>(it)
        } catch (e: Exception) {
            null // Handle error gracefully
        }
    }
}
