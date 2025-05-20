package com.ahrorovk.labwork.presentation.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovk.labwork.R
import com.ahrorovk.labwork.presentation.components.CustomButton
import com.ahrorovk.labwork.presentation.components.CustomTextField

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(state.authResponseState.response) {
        if (state.authResponseState.response?.exitCode == 200) {
            onEvent(LoginEvent.ShowPreload)
        } else {
            Toast.makeText(
                context,
                state.authResponseState.response?.message ?: state.authResponseState.error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.login), fontSize = 30.sp)
            Spacer(Modifier.padding(20.dp))
            CustomTextField(
                Modifier,
                state.login,
                "username"
            ) {
                onEvent(LoginEvent.OnLoginChange(it))
            }
            Spacer(Modifier.padding(12.dp))
            CustomTextField(
                Modifier,
                state.password,
                "password",
                keyboardType = KeyboardType.Password
            ) {
                onEvent(LoginEvent.OnPasswordChange(it))
            }
            Spacer(Modifier.padding(12.dp))
            CustomButton(
                Modifier,
                "Login",
                16,
                isLoading = state.authResponseState.loading || state.isPreShowLoading
            ) {
                onEvent(LoginEvent.Login)
            }
            Spacer(Modifier.padding(12.dp))
            Text(
                stringResource(R.string.goToSignUp),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onEvent(LoginEvent.GoToSignUp)
                })
        }
    }
}