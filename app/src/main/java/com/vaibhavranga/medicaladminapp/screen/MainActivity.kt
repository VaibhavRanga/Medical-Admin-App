package com.vaibhavranga.medicaladminapp.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vaibhavranga.medicaladminapp.navigation.App
import com.vaibhavranga.medicaladminapp.screen.ui.theme.MedicalAdminAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicalAdminAppTheme {
                App()
            }
        }
    }
}
