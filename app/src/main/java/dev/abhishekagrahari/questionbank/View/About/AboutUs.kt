package dev.abhishekagrahari.questionbank.View.About

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AboutUsScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),// Light background for the screen
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // Introduction Card
        IntroductionCard()
        /*

        // Vision Card
        AboutCard(title = "Our Vision", description = "To create the best programming community.", icon = Icons.Filled.ThumbUp)



        // Mission Card
        AboutCard(title = "Our Mission", description = "To empower learners and developers to excel in the tech world.", icon = Icons.Filled.Build)



        // Our Story Card
        AboutCard(title = "Our Story", description = "Started as a small community of passionate developers, now growing steadily with like-minded individuals.", icon = Icons.Filled.Info)
*/
        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun IntroductionCard() {
    val description = "We are a passionate group of developers and learners who aim to create a strong community and empower people with knowledge."

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onSecondary // Text color based on theme
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Welcome to Programming Mess",
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = TextStyle(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            AboutCard(title = "Our Vision", description = "To create the best programming community.", icon = Icons.Filled.ThumbUp)
            AboutCard(title = "Our Mission", description = "To empower learners and developers to excel in the tech world.", icon = Icons.Filled.Build)
            AboutCard(title = "Our Story", description = "Started as a small community of passionate developers, now growing steadily with like-minded individuals.", icon = Icons.Filled.Info)
        }
    }
}

@Composable
fun AboutCard(title: String, description: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Handle click event here */ },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface, // Adaptive surface color
            contentColor = MaterialTheme.colorScheme.onSurface // Adaptive text color
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.secondary // Theme-based icon color
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Text Information
            Column {
                Text(
                    text = title,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = description,
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        }
    }
}