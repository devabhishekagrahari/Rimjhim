import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.abhishekagrahari.questionbank.DataConnection.assessAnswer
import dev.abhishekagrahari.questionbank.DataConnection.FeedbackResponse
import dev.abhishekagrahari.questionbank.model.Question
import dev.abhishekagrahari.questionbank.repository.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val _feedbackMap = MutableStateFlow<Map<String, FeedbackResponse>>(emptyMap())
    val feedbackMap: StateFlow<Map<String, FeedbackResponse>> = _feedbackMap

    private val _isLoadingMap = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val isLoadingMap: StateFlow<Map<String, Boolean>> = _isLoadingMap

    private val repo = QuestionRepository()
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    fun evaluateAnswer(question: String, userAnswer: String, expectedAnswer: String) {
        viewModelScope.launch {
            _isLoadingMap.value = _isLoadingMap.value.toMutableMap().apply {
                this[question] = true
            }

            val response = assessAnswer(userAnswer, expectedAnswer)

            _feedbackMap.value = _feedbackMap.value.toMutableMap().apply {
                this[question] = response
            }

            _isLoadingMap.value = _isLoadingMap.value.toMutableMap().apply {
                this[question] = false
            }
        }
    }

    fun loadQuestions() {
        viewModelScope.launch {
            val data = repo.getRandomQuestions()
            _questions.value = data
        }
    }
}
