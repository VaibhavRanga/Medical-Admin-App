package com.vaibhavranga.medicaladminapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vaibhavranga.medicaladminapp.model.User
import com.vaibhavranga.medicaladminapp.screen.AddProductScreen
import com.vaibhavranga.medicaladminapp.screen.AllOrdersScreen
import com.vaibhavranga.medicaladminapp.screen.HomeScreen
import com.vaibhavranga.medicaladminapp.screen.UserDetailsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val bottomNavItem = listOf(
        BottomNavItem(
            title = "Home",
            unSelectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Default.Home,
            route = Routes.HomeScreenRoute
        ),
        BottomNavItem(
            title = "Add Product",
            unSelectedIcon = Icons.Outlined.AddCircle,
            selectedIcon = Icons.Default.AddCircle,
            route = Routes.AddProductScreenRoute
        ),
        BottomNavItem(
            title = "Orders",
            unSelectedIcon = Icons.Outlined.CheckCircle,
            selectedIcon = Icons.Default.CheckCircle,
            route = Routes.AllOrdersScreenRoute
        )
    )
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Medical Admin")
                }
            )
        },
        bottomBar = {
            NavigationBar {
                bottomNavItem.forEachIndexed { index, bottomNavItem ->
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.route == bottomNavItem.route::class.qualifiedName } == true

                    NavigationBarItem(
                        selected = isSelected,
                        label = {
                            Text(text = bottomNavItem.title)
                        },
                        onClick = {
                            navController.navigate(bottomNavItem.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) {
                                    bottomNavItem.selectedIcon
                                } else {
                                    bottomNavItem.unSelectedIcon
                                },
                                contentDescription = bottomNavItem.title
                            )
                        }
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Routes.HomeScreenRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Routes.HomeScreenRoute> {
                HomeScreen(
                    navController = navController
                )
            }
            composable<Routes.UserDetailsScreenRoute> {
                val userData = it.toRoute<Routes.UserDetailsScreenRoute>()
                val user = User(
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
                UserDetailsScreen(
                    user = user,
                    navController = navController
                )
            }
            composable<Routes.AddProductScreenRoute> {
                AddProductScreen()
            }
            composable<Routes.AllOrdersScreenRoute> {
                AllOrdersScreen()
            }
        }
    }
}

data class BottomNavItem(
    val title: String,
    val unSelectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val route: Routes
)