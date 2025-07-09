package com.vaibhavranga.medicaladminapp.utils

sealed class Results<out T> {
    object Loading : Results<Nothing>()
    data class Error(val error: String) : Results<Nothing>()
    data class Success<out T>(val data: T) : Results<T>()
}