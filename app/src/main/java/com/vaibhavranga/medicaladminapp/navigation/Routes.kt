package com.vaibhavranga.medicaladminapp.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object HomeScreenRoute : Routes()

    @Serializable
    data class UserDetailsScreenRoute(
        val accountCreationDate: String? = null,
        val address: String? = null,
        val email: String? = null,
        val id: Int? = null,
        val isApproved: Int? = null,
        val isBlocked: Int? = null,
        val name: String? = null,
        val password: String? = null,
        val phoneNumber: String? = null,
        val pincode: String? = null,
        val userId: String? = null
    ) : Routes()

    @Serializable
    data object AddProductScreenRoute : Routes()

    @Serializable
    data object AllOrdersScreenRoute : Routes()
}