package dev.abhishekagrahari.questionbank.model


data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "professor" // Future-proofing for multi-user roles
)
