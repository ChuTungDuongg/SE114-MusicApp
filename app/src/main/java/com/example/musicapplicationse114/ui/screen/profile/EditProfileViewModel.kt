package com.example.musicapplicationse114.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplicationse114.common.enum.LoadStatus
import com.example.musicapplicationse114.model.UserUpdateRequest
import com.example.musicapplicationse114.repositories.Api
import com.example.musicapplicationse114.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileState(
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val avatar: String = "",
    val status: LoadStatus = LoadStatus.Init()
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val api: Api?,
    private val tokenManager: TokenManager?
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditProfileState())
    val uiState = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePhone(phone: String) {
        _uiState.value = _uiState.value.copy(phone = phone)
    }

    fun updateAvatar(avatar: String) {
        _uiState.value = _uiState.value.copy(avatar = avatar)
    }

    fun updateProfile() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
                val token = tokenManager?.getToken()
                if (api != null && !token.isNullOrBlank()) {
                    val result = api.updateProfile(token, UserUpdateRequest(
                        _uiState.value.username,
                        _uiState.value.email,
                        _uiState.value.phone,
                        _uiState.value.avatar
                    ))
                    if (result.isSuccessful) {
                        tokenManager?.saveUserName(_uiState.value.username)
                        _uiState.value = _uiState.value.copy(status = LoadStatus.Success())
                    } else {
                        _uiState.value = _uiState.value.copy(status = LoadStatus.Error("${'$'}{result.code()}"))
                    }
                } else {
                    _uiState.value = _uiState.value.copy(status = LoadStatus.Error("Token or API null"))
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message.toString()))
            }
        }
    }
}
