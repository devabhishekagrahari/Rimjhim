package dev.abhishekagrahari.questionbank.repository



import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dev.abhishekagrahari.questionbank.model.User
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val userCollection = db.collection("users")

    /** Add or update user info */
    suspend fun addUser(user: User): Boolean {
        return try {
            userCollection.document(user.id).set(user).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /** Fetch user by ID */
    suspend fun getUser(userId: String): User? {
        return try {
            val snapshot = userCollection.document(userId).get().await()
            snapshot.toObject<User>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
