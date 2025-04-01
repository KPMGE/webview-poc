package com.example.webviewpoc

import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface

class WebAppInterface(
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
