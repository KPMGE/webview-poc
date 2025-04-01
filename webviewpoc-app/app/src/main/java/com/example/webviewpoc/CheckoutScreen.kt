package com.example.webviewpoc

import PaymentFailureScreen
import PaymentSuccessScreen
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun CheckoutScreen(navController: NavController) {
    val viewModel: PaymentViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        onDispose { viewModel.cleanup() }
    }

    BackHandler(enabled = true) {
        viewModel.goBack()
    }

    Box(Modifier.fillMaxSize()) {
        if (uiState is PaymentUiState.Success) {
            PaymentSuccessScreen(
                onContinue = { navController.navigate("checkoutScreen") }
            )
        }

        if (uiState !is PaymentUiState.Failure && uiState !is PaymentUiState.Success) {
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).also { viewModel.initWebView(it) }
                },
                modifier = Modifier.fillMaxSize(),
                onRelease = { it.destroy() }
            )
        }

        if (uiState is PaymentUiState.Loading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }


        if (uiState is PaymentUiState.Failure) {
            PaymentFailureScreen(
                onRetry = { viewModel.retryPayment() },
                onCancel = { viewModel.retryPayment() }
            )
        }
    }
}