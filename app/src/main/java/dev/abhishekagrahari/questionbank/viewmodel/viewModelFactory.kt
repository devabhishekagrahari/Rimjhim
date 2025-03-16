package dev.abhishekagrahari.questionbank.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import dev.abhishekagrahari.questionbank.repository.QuestionRepository

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = QuestionRepository(FirebaseFirestore.getInstance())
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
