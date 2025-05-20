package com.ahrorovk.labwork.presentation.sign_up

sealed class SignUpEvent {
    data class OnSignUpChange(val login: String) : SignUpEvent()
    data class OnPasswordChange(val password: String) : SignUpEvent()
    data class OnIsPreShowLoadingChange(val loading: Boolean) : SignUpEvent()
    object SignUp : SignUpEvent()
    object ShowPreload : SignUpEvent()
    object GoToLogin : SignUpEvent()

}