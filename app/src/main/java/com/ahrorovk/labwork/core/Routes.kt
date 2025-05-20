package com.ahrorovk.labwork.core

import com.ahrorovk.labwork.core.Constants.ID_ARG


sealed class Routes(val route: String) {
    object LoginScreen : Routes("LoginScreen")
    object AddScreen : Routes("AddScreen/{${ID_ARG}}")
    object StartScreen : Routes("StartScreen")
    object SignUpScreen : Routes("SignUpScreen")
    object MainScreen : Routes("MainScreen")
    object UserScreen : Routes("UserScreen")
    object FilterScreen : Routes("FilterScreen")
}