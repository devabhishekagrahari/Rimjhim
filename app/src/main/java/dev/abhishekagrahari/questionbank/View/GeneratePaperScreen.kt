package dev.abhishekagrahari.questionbank.View


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.abhishekagrahari.questionbank.model.DifficultyLevel
import dev.abhishekagrahari.questionbank.viewmodel.QuestionPaperViewModel
import dev.abhishekagrahari.questionbank.viewmodel.QuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratePaperScreen(viewModel: QuestionPaperViewModel = viewModel(), context: Context , onPaperGenerated: () -> Unit) {

    var questionCount by remember { mutableStateOf("5") }
    var selectedDifficulty by remember { mutableStateOf(DifficultyLevel.EASY) }
    var expandedD by remember { mutableStateOf(false) }
    var expandedC by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }
    var createdBy  by remember { mutableStateOf("Dr.Manish Sir (Head Of BioChemistry Department)") }
    val categories by viewModel.categories.observeAsState(emptyList())

    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Number of Questions
            TextField(
                value = questionCount,
                onValueChange = { questionCount = it },
                label = { Text("Number of Questions") },
                singleLine = true
            )
            ExposedDropdownMenuBox(
                expanded = expandedD,
                onExpandedChange = { expandedD = it }
            ) {
                OutlinedTextField(
                    value = selectedDifficulty.label,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Difficulty") },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedD) }
                )
                ExposedDropdownMenu(
                    expanded = expandedD,
                    onDismissRequest = { expandedD = false }
                ) {
                    DifficultyLevel.entries.forEach { difficulty ->
                        DropdownMenuItem(
                            text = { Text(difficulty.label) },
                            onClick = {
                                selectedDifficulty = difficulty
                                expandedD = false
                            }
                        )
                    }
                }
            }
            ExposedDropdownMenuBox(
                expanded = expandedC,
                onExpandedChange = { expandedC = it }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Category") },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedC) }
                )
                ExposedDropdownMenu(
                    expanded = expandedC,
                    onDismissRequest = { expandedC = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                expandedC = false
                            }
                        )
                    }
                }
            }
            TextField(
                value = createdBy,
                onValueChange = { createdBy = it },
                label = { Text("Created By") },
                singleLine = true
            )

            // Generate Paper Button
            Button(
                onClick = {
                    val count = questionCount.toIntOrNull() ?: 2
                    viewModel.generateQuestionPaper(
                        category = selectedCategory,
                        difficulty = selectedDifficulty.toString(),
                        questionCount = count,
                        createdBy= createdBy
                    ) {
                        Toast.makeText(context, "Paper Generated Successfully!", Toast.LENGTH_SHORT).show()
                    }
                    onPaperGenerated()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generate Paper")
            }
        }
    }

