package com.example.my_application

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State


class ViewModel1 : ViewModel() {
    var squares by mutableStateOf(0)
    internal set

    fun addSquares() {
        squares++
    }
}