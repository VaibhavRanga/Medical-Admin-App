package com.vaibhavranga.medicaladminapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaibhavranga.medicaladminapp.viewModel.MainViewModel

@Composable
fun AllOrdersScreen(viewModel: MainViewModel = hiltViewModel()) {
    val allOrdersState = viewModel.allOrdersState.collectAsStateWithLifecycle()
    val approveOrderState = viewModel.approveOrderState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllOrders()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (allOrdersState.value.data?.message?.isEmpty() == true) {
            Text(text = "No data found")
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn {
                    items(items = allOrdersState.value.data?.message ?: emptyList()) { order ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Text(text = "Category: ${order.category}")
                                Text(text = "Order date: ${order.orderDate}")
                                Text(text = "Price: ${order.price}")
                                Text(text = "Product name: ${order.productName}")
                                Text(text = "Quantity: ${order.quantity}")
                                Text(text = "Total amount: ${order.totalAmount}")
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(text = "Approved")
                                    Switch(
                                        checked = order.isApproved == 1,
                                        onCheckedChange = {
                                            viewModel.approveOrder(orderId = order.orderId!!)
                                        },
                                        enabled = order.isApproved == 0
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        when {
            allOrdersState.value.isLoading -> {
                CircularProgressIndicator()
            }
            allOrdersState.value.error != null -> {
                Toast.makeText(context, allOrdersState.value.error, Toast.LENGTH_SHORT).show()
                viewModel.clearAllOrdersState()
            }
        }
        when {
            approveOrderState.value.isLoading -> {
                CircularProgressIndicator()
            }
            approveOrderState.value.error != null -> {
                Toast.makeText(context, approveOrderState.value.error, Toast.LENGTH_SHORT).show()
                viewModel.clearApproveOrderState()
            }
            approveOrderState.value.data != null -> {
                Toast.makeText(context, approveOrderState.value.data?.message, Toast.LENGTH_SHORT).show()
                viewModel.clearApproveOrderState()
                viewModel.getAllOrders()
            }
        }
    }
}