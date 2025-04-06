package dev.abhishekagrahari.questionbank.View.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.abhishekagrahari.questionbank.model.DifficultyLevel
import dev.abhishekagrahari.questionbank.model.Question
import dev.abhishekagrahari.questionbank.viewmodel.QuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionScreen(viewModel: QuestionViewModel = viewModel(), onNavigateBack: () -> Unit) {
    var questionText by remember { mutableStateOf("") }
    var answerText by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf(DifficultyLevel.EASY) }
    //var questionType by remember { mutableStateOf(QuestionType.MCQ) }
    var expanded by remember { mutableStateOf(false) }
    var expandedt by remember { mutableStateOf(false) }
    var newOptionText by remember { mutableStateOf("") }
    var options by remember { mutableStateOf(mutableListOf<String>()) }
    var createdBy by remember { mutableStateOf("admin") }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomTextField(
            value = questionText,
            onValueChange = { questionText = it },
            placeholder = "Enter Question"
        )

        CustomTextField(
            value = answerText,
            onValueChange = { answerText = it },
            placeholder = "Enter Answer"
        )

        CustomTextField(
            value = category,
            onValueChange = { category = it },
            placeholder = "Enter Category"
        )
        // Difficulty Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedDifficulty.label,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Difficulty") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFF5F5F5)) // Same as other text fields
                    .menuAnchor(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        tint = Color.Gray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFF5F5F5), // Matches other text fields
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
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
        {/*
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
                */
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
                        imageVector = Icons.Default.Add,
                        contentDescription = "Delete Option",
                        tint = Color.Red
                    )
                }
            }
        }

        CustomTextField(
            value = newOptionText,
            onValueChange = { newOptionText = it },
            placeholder = "Add Option"
        )

        Button(
            onClick = {
                if (newOptionText.isNotBlank()) {
                    options = options.toMutableList().apply { add(newOptionText) }
                    newOptionText = ""
                }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Add Option")
        }

        CustomTextField(
            value = createdBy,
            onValueChange = { createdBy = it },
            placeholder = "Created By"
        )

        Button(
            onClick = {
                val newQuestion = Question(
                    question = questionText,
                    answer = answerText,
                    category = category,
                    options = options,
                    createdBy = createdBy
                )
                viewModel.addQuestion(newQuestion) { success ->
                    if (success) onNavigateBack()
                }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Save Question")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, fontSize = 12.sp, color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFF5F5F5)),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { })
    )
}
