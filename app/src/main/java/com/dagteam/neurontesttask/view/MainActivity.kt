package com.dagteam.neurontesttask.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import com.dagteam.neurontesttask.view.navigation.AppNavigation
import com.dagteam.neurontesttask.view.ui.theme.BackgroundColor
import com.dagteam.neurontesttask.view.ui.theme.NeuronTestTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NeuronTestTaskTheme {
                Scaffold(
                    containerColor = BackgroundColor
                ) { innerPadding ->
                    AppNavigation(
                        paddingValues = innerPadding
                    )
                }
            }
        }
    }
}