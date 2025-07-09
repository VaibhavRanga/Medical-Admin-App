package com.vaibhavranga.medicaladminapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vaibhavranga.medicaladminapp.model.User
import com.vaibhavranga.medicaladminapp.navigation.Routes
import com.vaibhavranga.medicaladminapp.screen.ui.theme.MedicalAdminAppTheme
import com.vaibhavranga.medicaladminapp.viewModel.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel = hiltViewModel(), navController: NavController) {
    val allUsersState by viewModel.allUsersState.collectAsStateWithLifecycle()
    val approveUserState by viewModel.approveUserState.collectAsStateWithLifecycle()
    val blockUnblockUserState by viewModel.blockUnblockUserState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllUsers()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (allUsersState.data?.message?.isEmpty() == true) {
            Text(text = "No data to show")
        } else {
            Users(
                usersList = allUsersState.data?.message ?: emptyList(),
                onApprovedChange = { approveUserId ->
                    viewModel.approveUser(userId = approveUserId)
                },
                onBlockedChange = { block, userId ->
                    viewModel.blockUnblockUser(blockUser = block, userId = userId)
                },
                onUserCardClick = { userData ->
                    navController.navigate(
                        Routes.UserDetailsScreenRoute(
                            accountCreationDate = userData.accountCreationDate,
                            address = userData.address,
                            email = userData.email,
                            id = userData.id,
                            isApproved = userData.isApproved,
                            isBlocked = userData.isBlocked,
                            name = userData.name,
                            password = userData.password,
                            phoneNumber = userData.phoneNumber,
                            pincode = userData.pincode,
                            userId = userData.userId
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        when {
            allUsersState.isLoading -> CircularProgressIndicator()
            allUsersState.error.isNotBlank() -> {
                Toast.makeText(context, allUsersState.error, Toast.LENGTH_SHORT).show()
                viewModel.clearAllUsersState()
            }
        }
        when {
            approveUserState.isLoading -> CircularProgressIndicator()
            approveUserState.error.isNotBlank() -> {
                Toast.makeText(context, approveUserState.error, Toast.LENGTH_SHORT).show()
                viewModel.clearApproveUserState()
            }
            approveUserState.data?.message == "User approved successfully" -> {
                viewModel.getAllUsers()
            }
        }
        when {
            blockUnblockUserState.isLoading -> CircularProgressIndicator()
            blockUnblockUserState.error.isNotBlank() -> {
                Toast.makeText(context, blockUnblockUserState.error, Toast.LENGTH_SHORT).show()
                viewModel.clearBlockUnblockUserState()
            }
            blockUnblockUserState.data?.message != null -> {
                viewModel.getAllUsers()
            }
        }
    }
}

@Composable
fun Users(
    usersList: List<User>,
    onApprovedChange: (approveUserId: String) -> Unit,
    onBlockedChange: (block: Int, userId: String) -> Unit,
    onUserCardClick: (user: User) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        items(items = usersList) { user ->
            UserListItem(
                user,
                onApprovedChange = onApprovedChange,
                onBlockedChange = onBlockedChange,
                onUserCardClick = onUserCardClick
            )
        }
    }
}

@Composable
fun UserListItem(
    user: User,
    onApprovedChange: (approveUserId: String) -> Unit,
    onBlockedChange: (block: Int, userId: String) -> Unit,
    onUserCardClick: (user: User) -> Unit
) {
    Card(
        onClick = {
            onUserCardClick(user)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(0.6f)
            ) {
                Text(text = user.name!!, overflow = TextOverflow.Ellipsis)
                Text(text = user.email!!, overflow = TextOverflow.Ellipsis)
                Text(text = user.accountCreationDate!!)
            }
            VerticalDivider(
                Modifier.padding(horizontal = 8.dp)
            )
            Column(
                modifier = Modifier.weight(0.4f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Approved: ")
                    Switch(
                        checked = user.isApproved == 1,
                        onCheckedChange = { onApprovedChange(user.userId!!) },
                        enabled = user.isApproved == 0
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Blocked: ")
                    Switch(
                        checked = user.isBlocked == 1,
                        onCheckedChange = { onBlockedChange(if (user.isBlocked == 0) 1 else 0, user.userId!!) },
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    MedicalAdminAppTheme {
        Users(
            usersList = listOf(
                User(
                    accountCreationDate = "2025-01-19",
                    address = "address",
                    email = "name1@gmail.com",
                    id = 1,
                    isApproved = 0,
                    isBlocked = 0,
                    name = "name 1",
                    password = "12345678",
                    phoneNumber = "9896231234",
                    pincode = "123456",
                    userId = "e8bb3931-5147-4ac3-bd36-cf8d0e54f2f8"
                ),
                User(
                    accountCreationDate = "2025-01-19",
                    address = "address",
                    email = "name1@gmail.com",
                    id = 1,
                    isApproved = 0,
                    isBlocked = 0,
                    name = "name 1",
                    password = "12345678",
                    phoneNumber = "9896231234",
                    pincode = "123456",
                    userId = "e8bb3931-5147-4ac3-bd36-cf8d0e54f2f8"
                ),
                User(
                    accountCreationDate = "2025-01-19",
                    address = "address",
                    email = "name1@gmail.com",
                    id = 1,
                    isApproved = 1,
                    isBlocked = 0,
                    name = "name 1",
                    password = "12345678",
                    phoneNumber = "9896231234",
                    pincode = "123456",
                    userId = "e8bb3931-5147-4ac3-bd36-cf8d0e54f2f8"
                ),
                User(
                    accountCreationDate = "2025-01-19",
                    address = "address",
                    email = "name1@gmail.com",
                    id = 1,
                    isApproved = 0,
                    isBlocked = 1,
                    name = "name 1",
                    password = "12345678",
                    phoneNumber = "9896231234",
                    pincode = "123456",
                    userId = "e8bb3931-5147-4ac3-bd36-cf8d0e54f2f8"
                )
            ),
            onApprovedChange = { approveUserId ->

            },
            onBlockedChange = { block, userId ->
                
            },
            onUserCardClick = {

            }
        )
    }
}