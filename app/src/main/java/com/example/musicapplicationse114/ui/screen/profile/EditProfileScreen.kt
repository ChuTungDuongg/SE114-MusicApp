package com.example.musicapplicationse114.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicapplicationse114.MainViewModel
import com.example.musicapplicationse114.common.enum.LoadStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = hiltViewModel(),
    mainViewModel: MainViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.status) {
        if (uiState.status is LoadStatus.Success) {
            navController.navigate("profile") {
                popUpTo("editProfile") { inclusive = true }
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.username,
                onValueChange = { viewModel.updateUsername(it) },
                label = { Text("Username") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    containerColor = Color(0xFF333333),
                    unfocusedBorderColor = Color(0xFF333333),
                    focusedBorderColor = Color(0xFF4169E1)
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("Email") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    containerColor = Color(0xFF333333),
                    unfocusedBorderColor = Color(0xFF333333),
                    focusedBorderColor = Color(0xFF4169E1)
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.phone,
                onValueChange = { viewModel.updatePhone(it) },
                label = { Text("Phone") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    containerColor = Color(0xFF333333),
                    unfocusedBorderColor = Color(0xFF333333),
                    focusedBorderColor = Color(0xFF4169E1)
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { viewModel.updateProfile() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4169E1))
            ) {
                Text("Save", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}
