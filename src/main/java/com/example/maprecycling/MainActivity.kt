package com.example.maprecycling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maprecycling.ui.theme.MapRecyclingTheme
import com.example.maprecycling.ui.ui.HomeScreen
import com.example.maprecycling.ui.ui.LoginScreen
import com.example.maprecycling.ui.ProfileScreen
import com.example.maprecycling.ui.ui.OSMMapScreen
import com.example.maprecycling.ui.ui.RegisterScreen
import com.example.maprecycling.ui.ui.theme.DicasScreen
import org.maplibre.android.MapLibre


import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        MapLibre.getInstance(this)
        enableEdgeToEdge()

        setContent {
            MapRecyclingTheme {

                val navController = rememberNavController()

                NavHost(navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                    composable("profile") { ProfileScreen(navController) }
                    composable("dicas") { DicasScreen(navController) }
                    composable("mapa") { OSMMapScreen(navController) }
                }
            }
        }
    }
}