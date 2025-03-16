package dev.abhishekagrahari.questionbank.View

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.material3.DividerDefaults.color

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.abhishekagrahari.questionbank.R

@Composable
fun ContactUsScreen() {
    val primaryColor = Color(0xFF6200EA) // Custom Primary Color

    // Outer column for the whole screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFE3F2FD)),
        horizontalAlignment = Alignment.Start
    ) {

        // Contact Information Cards


        ContactItem("Email", "devabhishekagrahari@gmail.com", Icons.Filled.Email)

        ContactItem("Address", "Atif Vihar , Uattardhona , Uttar Pradesh 226028, India ", Icons.Filled.LocationOn)

        ContactItem("Instagram", "@abhishek.a.grahari", Icons.Filled.Favorite)
        ContactItem("Linked In", "/in/devabhishekagrahari/" , Icons.Filled.FavoriteBorder)

        Spacer(modifier = Modifier.height(16.dp))


    }
}

@Composable
fun ContactItem(label: String, value: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Handle click event here */ },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                contentDescription = label,
                modifier = Modifier.size(32.dp),
                tint = Color.Gray
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Text Information
            Column {
                Text(
                    text = label,
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
                Text(
                    text = value,
                    style = TextStyle(fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                )
            }
        }
    }
}