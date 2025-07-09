package com.vaibhavranga.medicaladminapp.model

data class AllOrdersResponse(
    val message: List<Order>?,
    val status: Int?
)