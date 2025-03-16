package dev.abhishekagrahari.questionbank.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.abhishekagrahari.questionbank.model.Question
import dev.abhishekagrahari.questionbank.model.QuestionPaper
import dev.abhishekagrahari.questionbank.viewmodel.QuestionPaperViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullPaperViewScreen(
    title : String ,
    createdBy : String  ,
    timestamp: Long ,
    totalMarks: Int ,
    difficultyDistribution : Map<String ,Int> ,
    questions : List<Question>
) {
        Box(modifier = Modifier.padding(3.dp)) {
                PaperDetailsView(title  , createdBy  ,timestamp , totalMarks , difficultyDistribution ,questions )
        }
    }


@Composable
fun PaperDetailsView(title : String , createdBy : String  ,timestamp: Long , totalMarks: Int , difficultyDistribution : Map<String ,Int> ,questions : List<Question>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
            .background(color = Color.White)
            ,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Created by: $createdBy",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Date: ${formatDate(timestamp)}",
            style = MaterialTheme.typography.bodySmall
        )

        Divider()

        Text("Total Marks: $totalMarks", style = MaterialTheme.typography.bodyLarge)

        Text("Difficulty Distribution:", style = MaterialTheme.typography.bodyLarge)
        difficultyDistribution.forEach { (difficulty, count) ->
            Text("$difficulty: $count questions", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Questions:", style = MaterialTheme.typography.bodyLarge)
        LazyColumn {
            items(questions) { question ->
                QuestionItem(question)
            }
        }
    }
}

@Composable
fun QuestionItem(question: Question) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Q: ${question.question}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            question.options?.forEachIndexed { index, option ->
                Text("${index + 1}. $option", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Answer: ${question.answer}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

