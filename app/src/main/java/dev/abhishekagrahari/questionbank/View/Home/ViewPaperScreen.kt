package dev.abhishekagrahari.questionbank.View.Home

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.abhishekagrahari.questionbank.viewmodel.QuestionPaperViewModel

import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavController
import dev.abhishekagrahari.questionbank.model.QuestionPaper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPaperScreen(viewModel: QuestionPaperViewModel , context: Context ,navController: NavController) {
    val papers by viewModel.papers.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { viewModel.loadQuestionPapers() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn {
                items(papers) { paper ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Paper ID: ${paper.id}", style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Paper Title: ${paper.title}", style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Questions: ${paper.questions.size}", style = MaterialTheme.typography.bodyMedium)
                            if(showDialog) {
                                PdfDownloadDialog(viewModel, showDialog, paper)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                //title : String , createdBy : String  ,timestamp: Long , totalMarks: Int , difficultyDistribution : Map<String ,Int> ,questions : List<Question>
                                //"fullQuestionPaper/{title}/{createdBy}/{timestamp}/{totalMarks}/{difficultyDistribution}/{questions}"
                               /* Button(
                                    onClick = { navController.navigate("fullQuestionPaper/${paper.title}/${paper.createdBy}/${paper.totalMarks}/${paper.difficultyDistribution}/${paper.questions}/${paper.timestamp}") },
                                    //"fullQuestionPaper/{title}/{createdBy}/{totalMarks}/{difficultyDistribution}/{questions}/{timeStamp}"
                                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                                ) {
                                    Text("View Paper")
                                }
*/
                                Button(
                                    onClick = { showDialog = true
                                    }
                                ) {
                                    Text("Export to PDF")
                                }
                          }
                        }
                    }
                }
            }
        }
    }
/*
// Enter File Name
OutlinedTextField(
value = fileName,
onValueChange = { fileName = it },
label = { Text("Enter File Name") },
singleLine = true,
modifier = Modifier.fillMaxWidth()
)
Spacer(modifier = Modifier.height(20.dp))

// Save as PDF Button
Button(
onClick = {
    val safeFileName = if (fileName.text.isBlank()) "ExtractedText.pdf"
    else "${fileName.text}.pdf"
    pdfFilePath = saveAsPDF(context, extractedText, safeFileName)
}
) {
    Text("Save as PDF")
}
}
Spacer(modifier = Modifier.height(20.dp))
}

item {
    // Download PDF Button after PDF is Saved
    pdfFilePath?.let { path ->
        Button(onClick = { openPDF(context, path) }) {
            Text("Download PDF")
        }
    }
}
}
}
*/

@Composable
fun PdfDownloadDialog(viewModel: QuestionPaperViewModel , showDialog1: Boolean , paper: QuestionPaper) {
    var showDialog by remember { mutableStateOf(showDialog1) }
    var fileName by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showDialog = true }) {
            Text("Download PDF")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Enter Filename") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = fileName,
                            onValueChange = { fileName = it },
                            label = { Text("File Name") }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (fileName.isNotBlank()) {
                                viewModel.generateQuestionPaperPdf(context = context , fileName = fileName , questionPaper =paper )
                                showDialog = false
                            }
                        }
                    ) {
                        Text("Download")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}




