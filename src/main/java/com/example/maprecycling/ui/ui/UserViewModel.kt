package com.example.maprecycling.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf

class UserViewModel : ViewModel() {

    var name = mutableStateOf("Usuário")
    var email = mutableStateOf("usuario@gmail.com")
    var phone = mutableStateOf("(00) 00000-0000")

    fun updateProfile(newName: String, newEmail: String, newPhone: String) {
        name.value = newName
        email.value = newEmail
        phone.value = newPhone
    }
}
