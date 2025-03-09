package com.example.trabajo08_gil_calvo_odin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class WebViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Buscar los elementos dentro de la vista del Fragment
        val webView = view.findViewById<WebView>(R.id.webView)
        val btnIr = view.findViewById<Button>(R.id.btnIr)
        val etUrl = view.findViewById<EditText>(R.id.etUrl)

        // Configurar WebView
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        // Cargar URL al hacer clic en el botón
        btnIr.setOnClickListener {
            val url = etUrl.text.toString()
            if (url.isNotEmpty()) {
                webView.loadUrl("https://$url")
            }
        }
    }
}