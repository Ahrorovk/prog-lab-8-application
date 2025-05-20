package com.ahrorovk.labwork.app.navigation

sealed class NavigationEvent {
    object ShowLabWorks : NavigationEvent()
    object ClearToken : NavigationEvent()
}