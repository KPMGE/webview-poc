package com.example.webviewpoc

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.Modifier

class WebAppInterface(
    private val context: Context,
    private val onPaymentSuccess: () -> Unit,
    private val onPaymentFailure: () -> Unit
) {
    @JavascriptInterface
    fun paymentSuccess() {
        Handler(Looper.getMainLooper()).post {
            onPaymentSuccess()
        }
    }

    @JavascriptInterface
    fun paymentFailure() {
        Handler(Looper.getMainLooper()).post {
            onPaymentFailure()
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CheckoutWebViewCallback(
    paymentUrl: String,
    onPaymentSuccess: () -> Unit,
    onPaymentFailure: () -> Unit,
    modifier: Modifier = Modifier
) {
    var webView by remember { mutableStateOf<WebView?>(null) }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                addJavascriptInterface(
                    WebAppInterface(context, onPaymentSuccess, onPaymentFailure),
                    "Android"
                )
                webViewClient = object : WebViewClient() {
                    // This prevents redirects to external browsers
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest
                    ): Boolean {
                        // Handle all URLs within the WebView
                        return false
                    }

                    // For older Android versions (deprecated but still needed for API < 24)
                    @Deprecated("For API < 24")
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        return false
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