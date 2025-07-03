package com.example.musicapplicationse114.model

data class UserUpdateRequest(
    val username: String,
    val email: String,
    val phone: String,
    val avatar: String?
)
