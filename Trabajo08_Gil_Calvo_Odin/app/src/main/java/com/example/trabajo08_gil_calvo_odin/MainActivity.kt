package com.example.trabajo08_gil_calvo_odin

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCalculadora = findViewById<Button>(R.id.btnCalculadora)
        val btnNavegador = findViewById<Button>(R.id.btnNavegador)

        // Cargar WebViewFragment al iniciar la app
        loadFragment(WebViewFragment())

        btnCalculadora.setOnClickListener {
            loadFragment(CalculadoraFragment())
        }

        btnNavegador.setOnClickListener {
            // Recargar el fragmento WebView cada vez que se pulse el botón
            loadFragment(WebViewFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}