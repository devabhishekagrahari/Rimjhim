package dev.abhishekagrahari.questionbank.View


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.abhishekagrahari.questionbank.model.DifficultyLevel
import dev.abhishekagrahari.questionbank.model.Question
import dev.abhishekagrahari.questionbank.model.QuestionType
import dev.abhishekagrahari.questionbank.viewmodel.QuestionViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionScreen(viewModel: QuestionViewModel = viewModel(), onNavigateBack: () -> Unit) {
    var questionText by remember { mutableStateOf("") }
    var answerText by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf(DifficultyLevel.EASY) }
    var questionType by remember { mutableStateOf(QuestionType.MCQ) }
    var expanded by remember { mutableStateOf(false) }
    var expandedt by remember { mutableStateOf(false) }
    var newOptionText by remember { mutableStateOf("") }
    var options by remember { mutableStateOf(mutableListOf<String>()) }
    var createdBy by remember { mutableStateOf("admin") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = questionText,
            onValueChange = { questionText = it },
            label = { Text("Enter Question") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = answerText,
            onValueChange = { answerText = it },
            label = { Text("Enter Answer") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Enter Category") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Difficulty Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        )
        {
            OutlinedTextField(
                value = selectedDifficulty.label,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Difficulty") },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DifficultyLevel.entries.forEach { difficulty ->
                    DropdownMenuItem(
                        text = { Text(difficulty.label) },
                        onClick = {
                            selectedDifficulty = difficulty
                            expanded = false
                        }
                    )
                }
            }
        }

            //type drop down menu
        ExposedDropdownMenuBox(
                expanded = expandedt,
                onExpandedChange = { expandedt = it }
            )
        {
                OutlinedTextField(
                    value = questionType.label,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Type Of Question:") },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                )
                ExposedDropdownMenu(
                    expanded = expandedt,
                    onDismissRequest = { expandedt = false }
                ) {
                    QuestionType.entries.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.label) },
                            onClick = {
                                questionType = type
                                expandedt = false
                            }
                        )
                    }
                }
        }

        Spacer(modifier = Modifier.height(20.dp))

            // Options Section
        Text("Options:", style = MaterialTheme.typography.titleMedium)
        options.forEachIndexed { index, option ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(option, modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        options = options.toMutableList().apply { removeAt(index) }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Option"
                        )
                    }
                }
            }

        OutlinedTextField(
                value = newOptionText,
                onValueChange = { newOptionText = it },
                label = { Text("Add Option") },
                modifier = Modifier.fillMaxWidth()
        )


        Button(
                onClick = {
                    if (newOptionText.isNotBlank()) {
                        options = options.toMutableList().apply { add(newOptionText) }
                        newOptionText = "" // Clear input after adding
                    }
                },
                modifier = Modifier.padding(top = 10.dp)
        ) {
                Text("Add Option")
        }

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
                value = createdBy,
                onValueChange = { createdBy = it },
                label = { Text("Created By") },
                modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
                onClick = {
                    val newQuestion = Question(
                        question = questionText,
                        answer = answerText,
                        category = category,
                        difficulty = selectedDifficulty.name,  // Store difficulty as a string
                        options = options,
                        createdBy = createdBy
                    )
                    viewModel.addQuestion(newQuestion) { success ->
                        if (success) onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
        ) {
                Text("Save Question")
        }
    }
}



