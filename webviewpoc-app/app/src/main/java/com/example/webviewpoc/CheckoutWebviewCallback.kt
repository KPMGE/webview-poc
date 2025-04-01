package com.example.webviewpoc

import android.annotation.SuppressLint
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.Modifier

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CheckoutWebViewCallback(
    paymentUrl: String,
    onPaymentSuccess: () -> Unit,
    onPaymentFailure: () -> Unit,
    onLoadingFailure: () -> Unit = {},
    onLoadingSuccess: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var webView by remember { mutableStateOf<WebView?>(null) }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                addJavascriptInterface(
                    WebAppInterface(onPaymentSuccess, onPaymentFailure),
                    "Android"
                )
                webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        onLoadingFailure()
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        onLoadingSuccess()
                    }

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