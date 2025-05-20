package com.ahrorovk.labwork.app.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ahrorovk.labwork.app.navigation.components.LabWorkBottomBar
import com.ahrorovk.labwork.core.Constants.ID_ARG
import com.ahrorovk.labwork.core.Routes
import com.ahrorovk.labwork.core.doesScreenHaveBottomBar
import com.ahrorovk.labwork.core.doesScreenHavePopBack
import com.ahrorovk.labwork.core.doesScreenHaveTopBar
import com.ahrorovk.labwork.core.getTopBarTitle
import com.ahrorovk.labwork.presentation.add.AddEvent
import com.ahrorovk.labwork.presentation.add.AddScreen
import com.ahrorovk.labwork.presentation.add.AddViewModel
import com.ahrorovk.labwork.presentation.components.CustomIconButton
import com.ahrorovk.labwork.presentation.login.LoginEvent
import com.ahrorovk.labwork.presentation.login.LoginScreen
import com.ahrorovk.labwork.presentation.login.LoginViewModel
import com.ahrorovk.labwork.presentation.main.MainEvent
import com.ahrorovk.labwork.presentation.main.MainScreen
import com.ahrorovk.labwork.presentation.main.MainViewModel
import com.ahrorovk.labwork.presentation.sign_up.SignUpEvent
import com.ahrorovk.labwork.presentation.sign_up.SignUpScreen
import com.ahrorovk.labwork.presentation.sign_up.SignUpViewModel
import com.ahrorovk.labwork.presentation.start.StartScreen
import com.ahrorovk.labwork.presentation.user.UserScreen
import com.ahrorovk.labwork.presentation.user.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navigationState: NavigationState,
    onEvent: (NavigationEvent) -> Unit
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (doesScreenHaveTopBar(currentScreen))
                TopAppBar(
                    colors = TopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        scrolledContainerColor = MaterialTheme.colorScheme.primary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = getTopBarTitle(currentScreen),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    navigationIcon = {
                        if (doesScreenHavePopBack(currentScreen)) {
                            CustomIconButton(icon = Icons.Filled.KeyboardArrowLeft) {
                                navController.popBackStack()
                            }
                        }
                    },
                    actions = {
                        if (currentScreen == Routes.UserScreen.route) {
                            CustomIconButton(icon = Icons.Default.ExitToApp) {
                                onEvent(NavigationEvent.ClearToken)
                                navController.navigate(Routes.LoginScreen.route) {
                                    popUpTo(Routes.UserScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                )
        }, floatingActionButton = {
            if (currentScreen == Routes.MainScreen.route) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    FloatingActionButton({
                        navController.navigate(
                            Routes.AddScreen.route.replace(
                                "{${ID_ARG}}", "-1"
                            )
                        )
                    }) {
                        Icon(Icons.Default.AddCircle, "addLabWork")
                    }
                    FloatingActionButton({
                        navController.navigate(
                            Routes.AddScreen.route.replace(
                                "{${ID_ARG}}", "-2"
                            )
                        )
                    }) {
                        Icon(Icons.Default.AddCircleOutline, "addIfMaxLabWork")
                    }
                }
            }
        },
        bottomBar = {
            if (doesScreenHaveBottomBar(currentScreen)) {
                LabWorkBottomBar(navController)
            }
        }
    ) { it_ ->
        NavHost(navController, startDestination = Routes.StartScreen.route) {
            composable(route = Routes.LoginScreen.route) {

                val viewModel = hiltViewModel<LoginViewModel>()
                val state = viewModel.state.collectAsState()
                LaunchedEffect(navigationState.response) {
                    if (navigationState.response.response != null && navigationState.response.response.exitCode == 200) {
                        navController.navigate(Routes.MainScreen.route) {
                            popUpTo(Routes.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            navigationState.response.response?.message
                                ?: navigationState.response.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                LoginScreen(state.value) { event ->
                    when (event) {
                        LoginEvent.ShowPreload -> {
                            onEvent(NavigationEvent.ShowLabWorks)
                        }

                        LoginEvent.GoToSignUp -> {
                            navController.navigate(Routes.SignUpScreen.route) {
                                popUpTo(Routes.LoginScreen.route) {
                                    inclusive = true
                                }
                            }
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            }
            composable(route = Routes.SignUpScreen.route) {

                val viewModel = hiltViewModel<SignUpViewModel>()

                val state = viewModel.state.collectAsState()
                LaunchedEffect(navigationState.response) {
                    if (navigationState.response.response != null && navigationState.response.response.exitCode == 200) {
                        navController.navigate(Routes.MainScreen.route) {
                            popUpTo(Routes.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            navigationState.response.response?.message
                                ?: navigationState.response.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                SignUpScreen(state.value) { event ->
                    when (event) {
                        SignUpEvent.ShowPreload -> {
                            onEvent(NavigationEvent.ShowLabWorks)
                        }

                        SignUpEvent.GoToLogin -> {
                            navController.navigate(Routes.LoginScreen.route) {
                                popUpTo(Routes.SignUpScreen.route) {
                                    inclusive = true
                                }
                            }
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            }

            composable(route = Routes.StartScreen.route) {
                LaunchedEffect(navigationState.tokenState.isNotEmpty()) {
                    onEvent(NavigationEvent.ShowLabWorks)
                }
                LaunchedEffect(navigationState) {
                    if (navigationState.response.response?.exitCode == 200) {
                        navController.navigate(Routes.MainScreen.route) {
                            popUpTo(Routes.StartScreen.route) {
                                inclusive = true
                            }
                        }
                    } else if (navigationState.response.response?.exitCode != 200) {
                        navController.navigate(Routes.LoginScreen.route) {
                            popUpTo(Routes.StartScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
                StartScreen()
            }
            composable(Routes.MainScreen.route) {
                val viewModel = hiltViewModel<MainViewModel>()
                val state = viewModel.state.collectAsState()
                LaunchedEffect(true) {
                    viewModel.onEvent(MainEvent.OnLabWorkResponseStateChange(navigationState.response))
                }
                MainScreen(state.value) { event ->
                    when (event) {
                        is MainEvent.OnSelectedLabWorkIdChange -> {
                            navController.navigate(
                                Routes.AddScreen.route.replace(
                                    "{${ID_ARG}}", "${event.id}"
                                )
                            )
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            }
            composable(
                Routes.AddScreen.route, arguments = listOf(
                    navArgument(ID_ARG) {
                        type = NavType.IntType
                    }
                )) { backstackEntry ->
                val id = backstackEntry.arguments?.getInt(ID_ARG) ?: -1
                val viewModel = hiltViewModel<AddViewModel>()
                LaunchedEffect(true) {
                    viewModel.onEvent(AddEvent.AddIfMaxLabWork)
                }
                LaunchedEffect(id) {
                    viewModel.onEvent(AddEvent.OnIdChange(id))
                    if (id > 0) {
                        navigationState.response.response?.responseObj?.filter { it.id.toInt() == id }
                            ?.last()
                            ?.let { AddEvent.OnLabWorkChange(it) }?.let { viewModel.onEvent(it) }
                    }
                }
                val state = viewModel.state.collectAsState()
                AddScreen(state.value) { event ->
                    when (event) {
                        AddEvent.BackToMain -> {
                            onEvent(NavigationEvent.ShowLabWorks)
                            navController.popBackStack()
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            }
            composable(Routes.UserScreen.route) {
                val viewModel = hiltViewModel<UserViewModel>()
                val state = viewModel.state.collectAsState()

                UserScreen(state.value) { event ->
                    when (event) {
                        else -> viewModel.onEvent(event)
                    }
                }
            }
        }
    }
}