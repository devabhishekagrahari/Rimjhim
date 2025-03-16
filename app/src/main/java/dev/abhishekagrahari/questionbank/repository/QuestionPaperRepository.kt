package dev.abhishekagrahari.questionbank.repository



import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dev.abhishekagrahari.questionbank.model.Question
import dev.abhishekagrahari.questionbank.model.QuestionPaper
import kotlinx.coroutines.tasks.await

class QuestionPaperRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val paperCollection = db.collection("question_papers")

    /** Add a new question paper */
    suspend fun addQuestionPaper(paper: QuestionPaper): Boolean {
        return try {
            val docRef = paperCollection.document()
            val paperWithId = paper.copy(id = docRef.id)
            docRef.set(paperWithId).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /** Fetch all question papers */

    suspend fun getQuestionPapers(): List<QuestionPaper> {
        return try {
            val snapshot = paperCollection.get().await()

            if (snapshot.isEmpty) {
                println("Firestore: No question papers found.")
                return emptyList()
            }

            snapshot.documents.mapNotNull { doc ->
                val paper = doc.toObject(QuestionPaper::class.java)
                println("Firestore: Loaded paper - ${paper?.title}")
                paper
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Firestore Error: ${e.message}")
            emptyList()
        }
    }
    suspend fun getQuestionCategories(): List<String> {
        return try {
            val snapshot = db.collection("questions").get().await()

            // Extract and remove duplicates
            snapshot.documents.mapNotNull { it.getString("category") }.distinct()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // Return an empty list in case of failure
        }
    }

    suspend fun getQuestionsByCategoryAndDifficulty(category: String, difficulty: String): List<Question> {
        return try {
            val result = db.collection("questions")
                .whereEqualTo("category", category)
                .whereEqualTo("difficulty", difficulty)
                .get()
                .await() // Convert to a coroutine-friendly call

            result.documents.mapNotNull { it.toObject(Question::class.java) }
        } catch (e: Exception) {
            emptyList() // Return an empty list in case of failure
        }
    }
    suspend fun saveQuestionPaper(paper: QuestionPaper) {
        db.collection("question_papers").document(paper.id)
            .set(paper)
            .await()
    }
}
