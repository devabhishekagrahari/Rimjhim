package dev.abhishekagrahari.questionbank.View


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.abhishekagrahari.questionbank.Screen
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Question Bank") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navController.navigate(Screen.AddQuestion.route) }) {
                Text("Add Question")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(Screen.QuestionList.route) }) {
                Text("View Questions")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(Screen.GeneratePaper.route) }) {
                Text("Generate Question Paper")
            }
        }
    }
}
*/


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Manage Your Questions",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomButton(
                text = "Add Question",
                icon = Icons.Default.Add,
                onClick = { navController.navigate(Screen.AddQuestion.route) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "View Questions",
                icon = Icons.Default.List,
                onClick = { navController.navigate(Screen.QuestionList.route) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "Generate Question Paper",
                icon = Icons.Default.Info,
                onClick = { navController.navigate(Screen.GeneratePaper.route) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "View Question Paper",
                icon = Icons.Default.ShoppingCart,
                onClick = { navController.navigate(Screen.ViewPaper.route) }
            )
        }
    }


@Composable
fun CustomButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 18.sp, color = Color.White)
    }
}
