import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.abhishekagrahari.questionbank.DataConnection.assessAnswer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val _feedbackscore = MutableStateFlow("")
    val feedbackscore: StateFlow<String> = _feedbackscore
    private val _feedbackresponse = MutableStateFlow("")
    val feedbackresponse: StateFlow<String> = _feedbackresponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun evaluateAnswer(userAnswer: String, expectedAnswer: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = assessAnswer(userAnswer, expectedAnswer)
            _feedbackscore.value = "Score: ${response.score}/10"
            _feedbackresponse.value ="Feedback: ${response.feedback}"
            _isLoading.value = false
        }
    }
}
