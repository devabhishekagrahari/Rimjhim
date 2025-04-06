package dev.abhishekagrahari.questionbank.View

import QuizViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuizScreen(viewModel: QuizViewModel, question: String, expectedAnswer: String) {
    var userAnswer by remember { mutableStateOf("") }

    val feedbackMap by viewModel.feedbackMap.collectAsState()
    val loadingMap by viewModel.isLoadingMap.collectAsState()

    val feedbackResponse = feedbackMap[question]
    val isLoading = loadingMap[question] ?: false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = question,
            fontSize = 18.sp,
            style = MaterialTheme.typography.titleMedium
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
                    viewModel.evaluateAnswer(question, userAnswer, expectedAnswer)
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
