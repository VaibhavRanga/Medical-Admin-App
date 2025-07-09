package com.vaibhavranga.medicaladminapp.repository

import com.vaibhavranga.medicaladminapp.api.ApiService
import com.vaibhavranga.medicaladminapp.model.AddProductResponse
import com.vaibhavranga.medicaladminapp.model.AllOrdersResponse
import com.vaibhavranga.medicaladminapp.model.ApproveOrderResponse
import com.vaibhavranga.medicaladminapp.model.ApproveUserResponse
import com.vaibhavranga.medicaladminapp.model.BlockUnblockUserResponse
import com.vaibhavranga.medicaladminapp.model.DeleteUserResponse
import com.vaibhavranga.medicaladminapp.model.GetAllUsersResponse
import com.vaibhavranga.medicaladminapp.utils.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getAllUsers(): Flow<Results<GetAllUsersResponse>> = flow {
        emit(Results.Loading)

        try {
            val response = apiService.getAllUsers()
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    fun approveUser(userId: String): Flow<Results<ApproveUserResponse>> = flow {
        emit(Results.Loading)

        try {
            val response = apiService.approveUser(userId)
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    fun blockUnblockUser(blockUser: Int, userId: String): Flow<Results<BlockUnblockUserResponse>> = flow {
        emit(Results.Loading)

        try {
            val response = apiService.blockUnblockUser(blockUser = blockUser, userId = userId)
            emit((Results.Success(response)))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    fun deleteUser(userId: String): Flow<Results<DeleteUserResponse>> = flow {
        emit(Results.Loading)

        try {
            val response = apiService.deleteUser(userId)
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    fun addProduct(name: String, price: Double, category: String, stock: Int): Flow<Results<AddProductResponse>> = flow {
        emit(Results.Loading)

        try {
            val response = apiService.addProduct(name = name, price = price, category = category, stock = stock)
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    fun getAllOrders(): Flow<Results<AllOrdersResponse>> = flow {
        emit(Results.Loading)

        try {
            val response = apiService.getAllOrders()
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    fun approveOrder(orderId: String): Flow<Results<ApproveOrderResponse>> = flow {
        emit(Results.Loading)

        try {
            val response = apiService.approveOrder(orderId)
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }
}