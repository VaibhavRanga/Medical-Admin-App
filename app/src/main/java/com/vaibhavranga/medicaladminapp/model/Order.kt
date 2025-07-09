package com.vaibhavranga.medicaladminapp.model

data class Order(
    val category: String?,
    val id: Int?,
    val isApproved: Int?,
    val message: String?,
    val orderDate: String?,
    val orderId: String?,
    val price: Double?,
    val productId: String?,
    val productName: String?,
    val quantity: Int?,
    val totalAmount: Double?,
    val userId: String?,
    val username: String?
)