package dev.abhishekagrahari.questionbank.View.Home



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.abhishekagrahari.questionbank.model.Question
import dev.abhishekagrahari.questionbank.viewmodel.QuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionListScreen(viewModel: QuestionViewModel = viewModel()) {
    val questions by viewModel.questions.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadQuestions() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "All Questions", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(questions) { question ->
                QuestionItem(question, viewModel)
            }
        }
    }
}

@Composable
fun QuestionItem(question: Question, viewModel: QuestionViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Q: ${question.question}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "A: ${question.answer}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Category: ${question.category}", style = MaterialTheme.typography.labelMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.deleteQuestion(question.id) {} },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete")
            }
        }
    }
}
