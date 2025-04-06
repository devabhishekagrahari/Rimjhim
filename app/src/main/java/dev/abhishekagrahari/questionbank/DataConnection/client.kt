package dev.abhishekagrahari.questionbank.DataConnection

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// Initialize Ktor HTTP Client
val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}

// API Key (Replace with your own)
const val GEMINI_API_KEY = "AIzaSyB4cQmSUnrdTHjGiFtzlwjhmPic6XpGrZE"
const val GEMINI_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$GEMINI_API_KEY"

// Request Data Model
@Serializable
data class GeminiRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig = GenerationConfig("application/json") // Ensure JSON response
)

@Serializable
data class Content(
    val parts: List<Part>
)

@Serializable
data class Part(
    val text: String
)

@Serializable
data class GenerationConfig(
    val response_mime_type: String
)

// Response Data Model
@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>?
)

@Serializable
data class Candidate(
    val content: Content?
)

@Serializable
data class FeedbackResponse(
    val feedback: String,
    val score: Int
)

// Function to Call Gemini API
suspend fun assessAnswer(userAnswer: String, expectedAnswer: String): FeedbackResponse {
    val prompt = """
        Evaluate the user's response: '$userAnswer' compared to the correct answer: '$expectedAnswer'.
        Return a JSON object with two fields:
        {
            "feedback": "Detailed feedback on the response.",
            "score": 7  // Score from 0 to 10
        }
    """.trimIndent()
    fun extractJsonFromMarkdown(input: String): String {
        return input
            .replace("```json", "")
            .replace("```", "")
            .trim()
    }


    return try {
        val response: HttpResponse = client.post(GEMINI_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(GeminiRequest(listOf(Content(listOf(Part(prompt))))))
        }


        val rawResponse = response.bodyAsText()
        Log.d("GeminiAPI", "Raw Response: $rawResponse")

        val result: GeminiResponse = response.body()
        // Extract text
        val rawText = result.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            ?: """{"feedback": "No response from AI", "score": 0}""" // fallback

        val cleanJson = extractJsonFromMarkdown(rawText)

        Json.decodeFromString(FeedbackResponse.serializer(), cleanJson).also {
            Log.d("GeminiAPI", "Parsed Response: $it")
        }
    } catch (e: Exception) {
        Log.e("GeminiAPI", "Error: ${e.localizedMessage}")
        FeedbackResponse("Error: ${e.localizedMessage}", 0)
    }
}
