package com.example.musicapplicationse114.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import com.example.musicapplicationse114.common.enum.LoadStatus
import com.example.musicapplicationse114.repositories.Api
import com.example.musicapplicationse114.auth.TokenManager
import com.example.musicapplicationse114.model.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    // val username: String = "",
    // val email: String = "",
    // val phone: String = "",
    // val avatar: String = "",
    // val birthday: String = "",
    // val gender: String = "",
    val user: UserResponse? = null,
    val status: LoadStatus = LoadStatus.Init()
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val api: Api?,
    private val tokenManager: TokenManager?
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
                val token = tokenManager?.getToken()
                if (api != null && !token.isNullOrBlank()) {
                    val result = api.getCurrentUser(token)
                    if (result.isSuccessful) {
                        val user = result.body()
                        _uiState.value = _uiState.value.copy(
                            // username = user?.username ?: "",
                            // email = user?.email ?: "",
                            // phone = user?.phone ?: "",
                            // avatar = user?.avatar ?: "",
                            // birthday = user?.birthday ?: "",
                            // gender = user?.gender ?: "",
                            user = user,
                            status = LoadStatus.Success()
                        )
                        user?.username?.let { tokenManager.saveUserName(it) }
                    } else {
                        val errorMsg = "API Error: ${result.code()} - ${result.message()}"
                        _uiState.value = _uiState.value.copy(status = LoadStatus.Error(errorMsg))
                        Log.e("ProfileViewModel", errorMsg)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(status = LoadStatus.Error("Token or API null"))
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message.toString()))
                Log.e("ProfileViewModel", "Exception loading profile", e)
            }
        }
    }
}
