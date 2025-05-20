package com.ahrorovk.labwork.presentation.login

sealed class LoginEvent {
    data class OnLoginChange(val login: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    data class OnIsPreShowLoadingChange(val loading: Boolean) : LoginEvent()
    object Login : LoginEvent()
    object ShowPreload : LoginEvent()
    object GoToSignUp : LoginEvent()

}