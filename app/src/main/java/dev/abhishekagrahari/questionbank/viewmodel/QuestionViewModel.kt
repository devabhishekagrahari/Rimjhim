package dev.abhishekagrahari.questionbank.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.ListItem
import com.itextpdf.layout.element.Paragraph
import dev.abhishekagrahari.questionbank.model.Question
import dev.abhishekagrahari.questionbank.model.QuestionPaper
import dev.abhishekagrahari.questionbank.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.slf4j.MDC.put

class QuestionViewModel(
    private val repository: QuestionRepository = QuestionRepository()
) : ViewModel() {

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    /** Fetch all questions from Firestore */
    fun loadQuestions() {
        viewModelScope.launch {
            _isLoading.value = true
            _questions.value = repository.getQuestions()
            _isLoading.value = false
        }
    }

    /** Add a new question */
    fun addQuestion(question: Question, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.addQuestion(question)
            onResult(success)
            if (success) loadQuestions() // Refresh list
        }
    }

    /** Delete a question */
    fun deleteQuestion(questionId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.deleteQuestion(questionId)
            onResult(success)
            if (success) loadQuestions()
        }
    }


}
