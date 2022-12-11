package com.imadev.plantindentifier.remote.models

data class RegisterModel(
    val name: String,
    val email: String,
    val password: String
)