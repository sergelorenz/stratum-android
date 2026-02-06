package com.bvfonaps.stratum

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bvfonaps.stratum.ui.navigation.StratumNavHost


@Composable
fun StratumApp(navController: NavHostController = rememberNavController()) {
    StratumNavHost(navController = navController)
}