package com.example.webviewpoc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavController) {
    // NOTE: Remember to run the chekcout server on the same network as your computer
    val paymentUrl = "http://<your machine ip address>:3000/checkout"

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Checkout") })
        }
    ) { padding ->
        // NOTE: Change to CheckoutWebView for the alternative solution
        CheckoutWebViewCallback(
            paymentUrl = paymentUrl,
            modifier = Modifier.fillMaxSize().padding(padding),
            onPaymentSuccess = {
                navController.navigate("paymentSuccessScreen")
            },
            onPaymentFailure = {
                navController.navigate("paymentFailureScreen")
            },
        )
    }
}
