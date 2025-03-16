package dev.abhishekagrahari.questionbank.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionPaper(
    val id: String = "",
    val title: String = "",
    val createdBy: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val questions: List<Question> = emptyList(), // List of question IDs
    val totalMarks: Int = 0,
    val difficultyDistribution: Map<String, Int> = mapOf() // { "Easy": 5, "Medium": 3, "Hard": 2 }
)
