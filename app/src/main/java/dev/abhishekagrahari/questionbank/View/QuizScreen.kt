package dev.abhishekagrahari.questionbank.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itextpdf.kernel.pdf.PdfName.Column
import com.itextpdf.kernel.pdf.PdfName.FontWeight
import dev.abhishekagrahari.questionbank.viewmodel.QuizViewModel

@Composable
fun QuizScreen(viewModel: QuizViewModel = QuizViewModel() , navController: NavController ) {
    var userAnswer by remember { mutableStateOf("") }
    val feedback by viewModel.feedback.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Introduce About your self. (Enter Your Answer )", fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

        // TextField for Answer Input
        OutlinedTextField(
            value = userAnswer,
            onValueChange = { userAnswer = it },
            label = { Text("Your Answer") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            maxLines = 5
        )

        // Submit Button
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
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Submit Answer")
            }
        }

        // Feedback Display
        Text(
            text = feedback,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}

