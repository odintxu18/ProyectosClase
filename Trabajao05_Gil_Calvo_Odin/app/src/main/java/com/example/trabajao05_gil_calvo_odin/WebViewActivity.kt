package com.example.trabajao05_gil_calvo_odin

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)

        val webView = findViewById<WebView>(R.id.webview)
        val url = intent.getStringExtra("url")

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true // Habilitar JS si es necesario
        webView.loadUrl(url ?: "https://example.com")
    }
}
