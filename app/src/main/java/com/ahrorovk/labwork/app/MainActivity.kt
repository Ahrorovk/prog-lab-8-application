package com.ahrorovk.labwork.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrorovk.labwork.app.navigation.Navigation
import com.ahrorovk.labwork.app.navigation.NavigationViewModel
import com.ahrorovk.labwork.app.ui.theme.LabWorkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabWorkTheme {
                val viewModel = hiltViewModel<NavigationViewModel>()
                val state = viewModel.state.collectAsState()

                Navigation(state.value) { event ->
                    when (event) {
                        else -> viewModel.onEvent(event)
                    }
                }
            }
        }
    }
}