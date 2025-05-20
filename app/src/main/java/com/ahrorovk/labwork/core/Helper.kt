package com.ahrorovk.labwork.core

fun doesScreenHaveBottomBar(currentScreen: String) =
    currentScreen != Routes.SignUpScreen.route &&
            currentScreen != Routes.LoginScreen.route &&
            currentScreen != Routes.StartScreen.route &&
            currentScreen != Routes.AddScreen.route

fun doesScreenHavePopBack(currentScreen: String): Boolean {
    return currentScreen != Routes.MainScreen.route &&
            currentScreen != Routes.LoginScreen.route &&
            currentScreen != Routes.SignUpScreen.route &&
            currentScreen != Routes.StartScreen.route &&
            currentScreen != Routes.UserScreen.route

}

fun doesScreenHaveTopBar(currentScreen: String): Boolean {
    return currentScreen != Routes.LoginScreen.route &&
            currentScreen != Routes.SignUpScreen.route &&
            currentScreen != Routes.StartScreen.route
}


fun getTopBarTitle(currentScreen: String): String {
    return when (currentScreen) {
        Routes.MainScreen.route -> "Main"
        Routes.FilterScreen.route -> "Filter"
        Routes.AddScreen.route -> "Add"
        Routes.UserScreen.route -> "Profile"
        else -> ""
    }
}
