package com.ananttonk.ayurvedaassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ananttonk.ayurvedaassignment.factory.viewModelFactory
import com.ananttonk.ayurvedaassignment.navigation.AyurvedaNavigation
import com.ananttonk.ayurvedaassignment.ui.screen.AyurvedaViewModel
import com.ananttonk.ayurvedaassignment.ui.theme.AyurvedaAssignmentTheme

class MainActivity : ComponentActivity() {
    private val viewModel: AyurvedaViewModel by viewModels {
        viewModelFactory {
            AyurvedaViewModel(
                AyurvedaApplication.instance.getModuleImpl().ayurvedaRepoImpl
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.insertDemoProductIfRequired{
            setContent {
                AyurvedaAssignmentTheme {
                    AyurvedaApp()
                }
            }
        }

    }
}

@Composable
fun AyurvedaApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        AyurvedaNavigation(navController)
    }
}

