package com.example.webviewpoc

import PaymentFailureScreen
import PaymentSuccessScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun PaymentApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "checkoutScreen") {
        composable("checkoutScreen") { CheckoutScreen(navController) }
        composable("paymentSuccessScreen") { PaymentSuccessScreen(
            onContinue = { navController.popBackStack() }
        ) }
        composable("paymentFailureScreen") { PaymentFailureScreen(
            onCancel = { navController.popBackStack() },
            onRetry = { navController.popBackStack() }
        ) }
    }
}