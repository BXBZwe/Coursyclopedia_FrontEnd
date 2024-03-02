// Result.kt
package com.example.courscyclopedia.ui.util

// In Result.kt
sealed class Result<out T> {


    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

