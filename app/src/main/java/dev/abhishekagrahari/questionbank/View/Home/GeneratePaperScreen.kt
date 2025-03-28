package dev.abhishekagrahari.questionbank.View.Home


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.abhishekagrahari.questionbank.model.DifficultyLevel
import dev.abhishekagrahari.questionbank.viewmodel.QuestionPaperViewModel

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
        CustomTextField(
            value = questionCount,
            onValueChange = { questionCount = it },
            placeholder = "Number of Questions"
        )
        /*
        // Select Difficulty Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedD,
            onExpandedChange = { expandedD = it }
        ) {
            OutlinedTextField(
                value = selectedDifficulty.label,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Difficulty") },
                modifier = Modifier
                    .width(280.dp) // Keeps it slim
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedD) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFF5F5F5),
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                shape = RoundedCornerShape(12.dp) // Slight rounding
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

         */
        // Difficulty Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedD,
            onExpandedChange = { expandedD = it }
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

        // Select Category Dropdown
        /*
        ExposedDropdownMenuBox(
            expanded = expandedC,
            onExpandedChange = { expandedC = it }
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Category") },
                modifier = Modifier
                    .width(280.dp) // Keeps it slim
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedC) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFF5F5F5),
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                shape = RoundedCornerShape(12.dp)
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

         */
        ExposedDropdownMenuBox(
            expanded = expandedC,
            onExpandedChange = { expandedC = it }
        ) {
            OutlinedTextField(
                value = selectedCategory.ifEmpty { "Select Category" },
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Category") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFF5F5F5)) // Matches Difficulty UI
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

        // Created By Field
        CustomTextField(
            value = createdBy,
            onValueChange = { createdBy = it },
            placeholder = "Created By"
        )

        // Generate Paper Button
        Button(
            onClick = {
                val count = questionCount.toIntOrNull() ?: 2
                viewModel.generateQuestionPaper(
                    category = selectedCategory,
                    difficulty = selectedDifficulty.toString(),
                    questionCount = count,
                    createdBy = createdBy
                ) {
                    Toast.makeText(context, "Paper Generated Successfully!", Toast.LENGTH_SHORT).show()
                }
                onPaperGenerated()
            },
            modifier = Modifier.width(280.dp)
        ) {
            Text("Generate Paper", fontSize = 16.sp)
        }
    }
}