package dev.abhishekagrahari.questionbank.repository



import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dev.abhishekagrahari.questionbank.model.Question
import kotlinx.coroutines.tasks.await

class QuestionRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val questionCollection = db.collection("questions")

    /** Add a new question to Firestore */
    suspend fun addQuestion(question: Question): Boolean {
        return try {
            val docRef = questionCollection.document()
            val questionWithId = question.copy(id = docRef.id)
            docRef.set(questionWithId).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /** Fetch all questions */
    suspend fun getQuestions(): List<Question> {
        return try {
            val snapshot = questionCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject<Question>() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /** Fetch questions by category */
    suspend fun getQuestionsByCategory(category: String): List<Question> {
        return try {
            val snapshot = questionCollection.whereEqualTo("category", category).get().await()
            snapshot.documents.mapNotNull { it.toObject<Question>() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /** Delete a question */
    suspend fun deleteQuestion(questionId: String): Boolean {
        return try {
            questionCollection.document(questionId).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
