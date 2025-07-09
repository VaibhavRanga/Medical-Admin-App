package com.vaibhavranga.medicaladminapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhavranga.medicaladminapp.model.AddProductResponse
import com.vaibhavranga.medicaladminapp.model.AllOrdersResponse
import com.vaibhavranga.medicaladminapp.model.ApproveOrderResponse
import com.vaibhavranga.medicaladminapp.model.ApproveUserResponse
import com.vaibhavranga.medicaladminapp.model.BlockUnblockUserResponse
import com.vaibhavranga.medicaladminapp.model.DeleteUserResponse
import com.vaibhavranga.medicaladminapp.model.GetAllUsersResponse
import com.vaibhavranga.medicaladminapp.repository.MainRepository
import com.vaibhavranga.medicaladminapp.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _allUsersState = MutableStateFlow(GetAllUsersState())
    val allUsersState = _allUsersState.asStateFlow()

    private val _approveUserState = MutableStateFlow(ApproveUserState())
    val approveUserState = _approveUserState.asStateFlow()

    private val _blockUnblockUserState = MutableStateFlow(BlockUnblockUserState())
    val blockUnblockUserState = _blockUnblockUserState.asStateFlow()

    private val _deleteUserState = MutableStateFlow(DeleteUserState())
    val deleteUserState = _deleteUserState.asStateFlow()

    private val _addProductState = MutableStateFlow(AddProductState())
    val addProductState = _addProductState.asStateFlow()

    private val _allOrdersState = MutableStateFlow(GetAllOrdersState())
    val allOrdersState = _allOrdersState.asStateFlow()

    private val _approveOrderState = MutableStateFlow(ApproveOrderState())
    val approveOrderState = _approveOrderState.asStateFlow()

    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getAllUsers().collect { response ->
                when (response) {
                    is Results.Loading -> _allUsersState.value = GetAllUsersState(isLoading = true)
                    is Results.Error -> _allUsersState.value =
                        GetAllUsersState(isLoading = false, error = response.error)

                    is Results.Success -> _allUsersState.value =
                        GetAllUsersState(isLoading = false, data = response.data)
                }
            }
        }
    }

    fun clearAllUsersState() {
        _allUsersState.update {
            it.copy(
                isLoading = false,
                error = ""
            )
        }
    }

    fun approveUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.approveUser(userId).collect { response ->
                when (response) {
                    is Results.Loading -> _approveUserState.value =
                        ApproveUserState(isLoading = true)

                    is Results.Error -> _approveUserState.value =
                        ApproveUserState(isLoading = false, error = response.error)

                    is Results.Success -> _approveUserState.value =
                        ApproveUserState(isLoading = false, data = response.data)
                }
            }
        }
    }

    fun clearApproveUserState() {
        _approveUserState.value = ApproveUserState()
    }

    fun blockUnblockUser(blockUser: Int, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.blockUnblockUser(blockUser = blockUser, userId = userId)
                .collect { response ->
                    when (response) {
                        is Results.Loading -> _blockUnblockUserState.value =
                            BlockUnblockUserState(isLoading = true)

                        is Results.Error -> _blockUnblockUserState.value =
                            BlockUnblockUserState(isLoading = false, error = response.error)

                        is Results.Success -> _blockUnblockUserState.value =
                            BlockUnblockUserState(isLoading = false, data = response.data)
                    }
                }
        }
    }

    fun clearBlockUnblockUserState() {
        _blockUnblockUserState.value = BlockUnblockUserState()
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteUser(userId).collect { response ->
                when (response) {
                    is Results.Loading -> _deleteUserState.value =
                        DeleteUserState(isLoading = true)

                    is Results.Error -> _deleteUserState.value =
                        DeleteUserState(isLoading = false, error = response.error)

                    is Results.Success -> _deleteUserState.value =
                        DeleteUserState(isLoading = false, data = response.data)
                }
            }
        }
    }

    fun clearDeleteUserState() {
        _deleteUserState.value = DeleteUserState()
    }

    fun addProduct(name: String, price: Double, category: String, stock: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.addProduct(
                name = name,
                price = price,
                category = category,
                stock = stock
            ).collect { response ->
                when (response) {
                    is Results.Loading -> _addProductState.value =
                        AddProductState(isLoading = true)

                    is Results.Error -> _addProductState.value =
                        AddProductState(isLoading = false, error = response.error)

                    is Results.Success -> _addProductState.value =
                        AddProductState(isLoading = false, data = response.data)
                }
            }
        }
    }

    fun clearAddProductState() {
        _addProductState.value = AddProductState()
    }

    fun getAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getAllOrders().collect { response ->
                when (response) {
                    is Results.Loading -> _allOrdersState.value =
                        GetAllOrdersState(isLoading = true)

                    is Results.Error -> _allOrdersState.value =
                        GetAllOrdersState(isLoading = false, error = response.error)

                    is Results.Success -> _allOrdersState.value =
                        GetAllOrdersState(isLoading = false, data = response.data)
                }
            }
        }
    }

    fun clearAllOrdersState() {
        _allOrdersState.value = GetAllOrdersState(
            isLoading = false,
            error = null
        )
    }

    fun approveOrder(orderId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.approveOrder(orderId).collect { response ->
                when (response) {
                    is Results.Loading -> _approveOrderState.value =
                        ApproveOrderState(isLoading = true)

                    is Results.Error -> _approveOrderState.value =
                        ApproveOrderState(isLoading = false, error = response.error)

                    is Results.Success -> _approveOrderState.value =
                        ApproveOrderState(isLoading = false, data = response.data)
                }
            }
        }
    }

    fun clearApproveOrderState() {
        _approveOrderState.value = ApproveOrderState()
    }
}

data class GetAllUsersState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: GetAllUsersResponse? = null
)

data class ApproveUserState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: ApproveUserResponse? = null
)

data class BlockUnblockUserState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: BlockUnblockUserResponse? = null
)

data class DeleteUserState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: DeleteUserResponse? = null
)

data class AddProductState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: AddProductResponse? = null
)

data class GetAllOrdersState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: AllOrdersResponse? = null
)

data class ApproveOrderState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: ApproveOrderResponse? = null
)