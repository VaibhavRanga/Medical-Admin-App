package com.vaibhavranga.medicaladminapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaibhavranga.medicaladminapp.viewModel.MainViewModel

@Composable
fun AddProductScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableDoubleStateOf(0.0) }
    var productCategory by remember { mutableStateOf("") }
    var productStock by remember { mutableIntStateOf(0) }
    val addProductState = viewModel.addProductState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "Add a product")
            OutlinedTextField(
                value = productName,
                onValueChange = {
                    productName = it
                },
                label = {
                    Text(text = "Product name")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = productPrice.toString(),
                onValueChange = {
                    productPrice = it.toDouble()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = {
                    Text(text = "Price")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = productCategory,
                onValueChange = {
                    productCategory = it
                },
                label = {
                    Text(text = "Category")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = productStock.toString(),
                onValueChange = {
                    productStock = it.toInt()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = {
                    Text(text = "Stock")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Button(
                onClick = {
                    if (productName.isNotBlank()
                        && productPrice != 0.0
                        && productCategory.isNotBlank()
                        && productStock != 0
                    ) {
                        viewModel.addProduct(
                            name = productName,
                            price = productPrice,
                            category = productCategory,
                            stock = productStock
                        )
                    } else {
                        Toast.makeText(context, "Please provide all details", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(text = "Save")
            }
        }
        when {
            addProductState.value.isLoading -> CircularProgressIndicator()
            addProductState.value.error != null -> {
                Toast.makeText(context, addProductState.value.error, Toast.LENGTH_SHORT).show()
                viewModel.clearAddProductState()
            }

            addProductState.value.data != null -> {
                Toast.makeText(context, addProductState.value.data?.message, Toast.LENGTH_SHORT)
                    .show()
                viewModel.clearAddProductState()
            }
        }
    }
}
