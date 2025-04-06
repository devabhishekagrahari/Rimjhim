package dev.abhishekagrahari.questionbank.View.Home

import QuizViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.abhishekagrahari.questionbank.View.QuizScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun QuizUI(viewModel: QuizViewModel ,navController: NavController) {
    val questions by viewModel.questions.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadQuestions() }
    LazyColumn {
        items(questions) { q ->
           EditableQuestionCard(
               questionText = q.question,
               answerText = q.answer,
               navController = navController,
               viewModel = viewModel
           )
        }
    }
}
@Composable
fun EditableQuestionCard(
    questionText: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    answerText: String,
    viewModel: QuizViewModel
) {
    var userAnswer by remember { mutableStateOf("") }

    val feedbackMap by viewModel.feedbackMap.collectAsState()
    val loadingMap by viewModel.isLoadingMap.collectAsState()

    val feedbackResponse = feedbackMap[questionText]
    val isLoading = loadingMap[questionText] ?: false

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = questionText,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = userAnswer,
                onValueChange = { userAnswer = it },
                label = { Text("Your Answer") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                maxLines = 5
            )

            Button(
                onClick = {
                    if (userAnswer.isNotBlank()) {
                        viewModel.evaluateAnswer(questionText, userAnswer, answerText)
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text("Submit Answer")
                }
            }

            feedbackResponse?.let {
                Text(
                    text = "Score: ${it.score}/10",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 12.dp)
                )

                Text(
                    text = it.feedback,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}




