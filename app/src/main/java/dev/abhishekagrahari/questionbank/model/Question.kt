package dev.abhishekagrahari.questionbank.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: String = "",
    val question: String = "",
    val options: List<String>? = null, // Only for MCQs
    val answer: String = "",
    val type: String = "", // "MCQ", "Short Answer", "Long Answer", "Diagram"
    val category: String = "",
    val difficulty: String = "", // "Easy", "Medium", "Hard"
    val imageUrl: String? = null, // Optional for diagram-based questions
    val createdBy: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
