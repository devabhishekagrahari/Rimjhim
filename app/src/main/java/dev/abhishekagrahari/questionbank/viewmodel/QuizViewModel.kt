package dev.abhishekagrahari.questionbank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.abhishekagrahari.questionbank.DataConnection.assessAnswer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val _feedback = MutableStateFlow("Your feedback will appear here.")
    val feedback: StateFlow<String> = _feedback.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun evaluateAnswer(userAnswer: String, expectedAnswer: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = assessAnswer(userAnswer, expectedAnswer)
            _feedback.value = result.toString()
            _isLoading.value = false
        }
    }

}
