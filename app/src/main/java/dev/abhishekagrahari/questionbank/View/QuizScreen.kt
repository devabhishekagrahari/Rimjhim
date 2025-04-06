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
import androidx.navigation.NavController


@Composable
fun QuizScreen(viewModel: QuizViewModel , navController: NavController) {
    var userAnswer by remember { mutableStateOf("") }

    val feedback by viewModel.feedbackresponse.collectAsState()
    val score by viewModel.feedbackscore.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Introduce yourself (Enter your answer):",
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
                    viewModel.evaluateAnswer(userAnswer, "The correct answer goes here.")
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

        if (feedback.isNotBlank()) {
            Text(
                text = score,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = feedback,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
