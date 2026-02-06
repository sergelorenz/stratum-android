package com.bvfonaps.stratum.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bvfonaps.stratum.ui.screens.splash.SplashDestination
import com.bvfonaps.stratum.ui.screens.splash.SplashScreen


@Composable
fun StratumNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination.route,
        modifier = modifier
    ) {
        composable(route = SplashDestination.route) {
            SplashScreen()
        }
    }
}