package com.vaibhavranga.medicaladminapp.model

data class User(
    val accountCreationDate: String?,
    val address: String?,
    val email: String?,
    val id: Int?,
    var isApproved: Int?,
    val isBlocked: Int?,
    val name: String?,
    val password: String?,
    val phoneNumber: String?,
    val pincode: String?,
    val userId: String?
)