package com.vaibhavranga.medicaladminapp.api

import com.vaibhavranga.medicaladminapp.model.AddProductResponse
import com.vaibhavranga.medicaladminapp.model.AllOrdersResponse
import com.vaibhavranga.medicaladminapp.model.ApproveOrderResponse
import com.vaibhavranga.medicaladminapp.model.ApproveUserResponse
import com.vaibhavranga.medicaladminapp.model.BlockUnblockUserResponse
import com.vaibhavranga.medicaladminapp.model.DeleteUserResponse
import com.vaibhavranga.medicaladminapp.model.GetAllUsersResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {

    @GET("getAllUsers")
    suspend fun getAllUsers(): GetAllUsersResponse

    @FormUrlEncoded
    @PATCH("approveUser")
    suspend fun approveUser(
        @Field("userId") userId: String
    ): ApproveUserResponse

    @FormUrlEncoded
    @PATCH("blockUnblockUser")
    suspend fun blockUnblockUser(
        @Field("blockUser") blockUser: Int,
        @Field("userId") userId: String
    ): BlockUnblockUserResponse

    @FormUrlEncoded
    @HTTP(
        method = "DELETE",
        path = "deleteUser",
        hasBody = true
    )
    suspend fun deleteUser(
        @Field("userId") userId: String
    ): DeleteUserResponse

    @FormUrlEncoded
    @POST("addProduct")
    suspend fun addProduct(
        @Field("name") name: String,
        @Field("price") price: Double,
        @Field("category") category: String,
        @Field("stock") stock: Int
    ): AddProductResponse

    @GET("getAllOrdersDetails")
    suspend fun getAllOrders(): AllOrdersResponse

    @FormUrlEncoded
    @PATCH("approveOrder")
    suspend fun approveOrder(
        @Field("orderId") orderId: String
    ): ApproveOrderResponse

    @FormUrlEncoded
    @PATCH("updateUserDetails")
    suspend fun updateUserDetails(
        @Field("address") address: String?,
        @Field("email") email: String?,
        @Field("name") name: String?,
        @Field("password") password: String?,
        @Field("phoneNumber") phoneNumber: String?,
        @Field("pincode") pincode: String?
    )
}