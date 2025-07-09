package com.vaibhavranga.medicaladminapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vaibhavranga.medicaladminapp.model.User
import com.vaibhavranga.medicaladminapp.viewModel.MainViewModel

@Composable
fun UserDetailsScreen(
    user: User,
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val deleteUserState by viewModel.deleteUserState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically
            ),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "Name: ${user.name!!}")
            Text(text = "Email: ${user.email!!}")
            Text(text = "Address: ${user.address!!}")
            Text(text = "Pincode: ${user.pincode}")
            Text(text = "Phone number: ${user.phoneNumber!!}")
            Text(text = "Account created on: ${user.accountCreationDate!!}")
        }
        Button(
            onClick = {
                viewModel.deleteUser(userId = user.userId!!)
            },
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
        ) {
            Text(text = "Delete User")
        }

        when {
            deleteUserState.isLoading -> CircularProgressIndicator()
            deleteUserState.error.isNotBlank() -> {
                Toast.makeText(context, deleteUserState.error, Toast.LENGTH_SHORT).show()
                viewModel.clearDeleteUserState()
            }
            deleteUserState.data != null -> {
                if (deleteUserState.data?.message == "User deleted") {
                    navController.navigateUp()
                }
                viewModel.clearDeleteUserState()
            }
        }
    }
}
