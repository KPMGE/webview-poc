package com.example.webviewpoc

import android.annotation.SuppressLint
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed class PaymentUiState {
    data object Loading : PaymentUiState()
    data object Loaded : PaymentUiState()
    data object Success : PaymentUiState()
    data class Failure(val message: String) : PaymentUiState()
}

class PaymentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<PaymentUiState>(PaymentUiState.Loading)
    private val paymentUrl = "http://192.168.18.16:3000/checkout"

    var uiState = _uiState.asStateFlow()

    @SuppressLint("StaticFieldLeak")
    private var webView: WebView? = null

    private fun onPaymentSuccess() {
        _uiState.update {
            PaymentUiState.Success
        }
    }

    private fun onPaymentFailure() {
        _uiState.update {
            PaymentUiState.Failure("Failed to load the payment page")
        }
    }

    private fun onLoadingSuccess() {
        _uiState.update {
            PaymentUiState.Loaded
        }
    }

    private fun onLoadingFailure() {
        _uiState.update {
            PaymentUiState.Failure("Failed to load the payment page")
        }
    }

    fun initWebView(webView: WebView) {
        this.webView = webView
        configureWebView(webView)
    }

    fun retryPayment() {
        _uiState.value = PaymentUiState.Loading
        webView?.reload()
    }

    fun goBack() {
        webView?.goBack()
    }

    fun cleanup() {
        webView?.apply {
            stopLoading()
            destroy()
        }
    }

    private fun configureWebView(webView: WebView) {
        webView.apply {
            addJavascriptInterface(
                WebAppInterface(::onPaymentSuccess, ::onPaymentFailure),
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

                override fun onReceivedHttpError(
                    view: WebView,
                    request: WebResourceRequest,
                    errorResponse: WebResourceResponse
                ) {
                    if (request.isForMainFrame) {
                        onLoadingFailure()
                    }
                }

                // This prevents redirects to external browsers
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest
                ): Boolean {
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
        }
    }
}

