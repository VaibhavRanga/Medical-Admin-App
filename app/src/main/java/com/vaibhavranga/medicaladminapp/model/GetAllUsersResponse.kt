package com.vaibhavranga.medicaladminapp.model

data class GetAllUsersResponse(
    val message: List<User>?,
    val status: Int?
)