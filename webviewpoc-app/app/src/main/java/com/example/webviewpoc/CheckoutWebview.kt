package com.example.webviewpoc

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.Modifier

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CheckoutWebView(
    paymentUrl: String,
    onPaymentSuccess: () -> Unit,
    onPaymentFailure: () -> Unit,
    modifier: Modifier = Modifier
) {
    var webView by remember { mutableStateOf<WebView?>(null) }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        when {
                            url?.contains("success") == true -> onPaymentSuccess()
                            url?.contains("failure") == true -> onPaymentFailure()
                        }
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(paymentUrl)
                webView = this
            }
        },
        modifier = modifier
    )

    BackHandler(enabled = true) {
        webView?.goBack()
    }
}