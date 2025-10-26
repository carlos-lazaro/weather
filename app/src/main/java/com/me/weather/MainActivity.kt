package com.me.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.me.weather.presentation.features.home.HomeScreen
import com.me.weather.presentation.features.search.SearchScreen
import com.me.weather.presentation.navigation.Router
import com.me.weather.presentation.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            WeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Router.Home,
                    ) {
                        composable<Router.Home> {
                            HomeScreen(
                                innerPadding = innerPadding,
                                goToSearch = {
                                    navController.navigate(Router.Search) {
                                        launchSingleTop = true
                                    }
                                },
                            )
                        }
                        composable<Router.Search> {
                            SearchScreen(
                                innerPadding = innerPadding,
                                onBack = {
                                    navController.navigateUp()
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
