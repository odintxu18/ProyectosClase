package com.example.trabajo11_gil_calvo_odin

import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import api.ApiService

import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var apiKeyEditText: EditText
    private lateinit var imageNumberTextViews: Array<TextView>
    private lateinit var showImageButton: Button
    private lateinit var marsImageView: ImageView
    private var selectedImageIndex = 0

    private val BASE_URL = "https://api.nasa.gov/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiKeyEditText = findViewById(R.id.apiKeyEditText)
        imageNumberTextViews = arrayOf(
            findViewById(R.id.imageNumber0),
            findViewById(R.id.imageNumber1),
            findViewById(R.id.imageNumber2)
        )
        showImageButton = findViewById(R.id.showImageButton)
        marsImageView = findViewById(R.id.marsImageView)

        setupImageNumberSelection()

        showImageButton.setOnClickListener {
            fetchMarsImage()
        }
    }

    private fun setupImageNumberSelection() {
        imageNumberTextViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                imageNumberTextViews.forEach { tv -> tv.setBackgroundResource(android.R.color.transparent) } // Deselecciona otros
                textView.setBackgroundResource(android.R.color.darker_gray) // Selecciona el actual
                selectedImageIndex = index
            }
        }
        imageNumberTextViews[selectedImageIndex].setBackgroundResource(android.R.color.darker_gray) // Seleccionar el primero por defecto
    }


    private fun fetchMarsImage() {
        val apiKey = apiKeyEditText.text.toString()
        if (apiKey.isBlank()) {
            apiKeyEditText.error = "API Key requerida"
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val apiService = retrofit.create(ApiService::class.java)
                val response = apiService.getMarsPhotos(1000, apiKey).execute() // sol = 1000 as per example

                if (response.isSuccessful) {
                    val curiosityData = response.body()
                    curiosityData?.photos?.let { photos ->
                        if (photos.isNotEmpty() && selectedImageIndex < photos.size) {
                            val selectedPhoto = photos[selectedImageIndex]
                            val imageUrlHttp = selectedPhoto.img_src
                            val imageUrlHttps = imageUrlHttp.replaceFirst("http", "https") // Replace http with https

                            runOnUiThread {
                                Picasso.get()
                                    .load(imageUrlHttps)
                                    .placeholder(R.drawable.nasa_logo) // Placeholder while loading
                                    .error(R.drawable.error_image) // Error image if load fails
                                    .fit() // Fit image to ImageView
                                    .centerCrop()
                                    .into(marsImageView)
                            }
                        } else {
                            runOnUiThread {
                                marsImageView.setImageResource(R.drawable.error_image) // Set error image
                                Log.e("API_Error", "No hay imagenes en la respuesta o indice invalido")
                            }
                        }
                    } ?: run {
                        runOnUiThread {
                            marsImageView.setImageResource(R.drawable.error_image) // Set error image
                            Log.e("API_Error", "Body de la respuesta nulo")
                        }
                    }
                } else {
                    runOnUiThread {
                        marsImageView.setImageResource(R.drawable.error_image) // Set error image
                        Log.e("API_Error", "Error en la llamada a la API: ${response.code()}")
                    }
                }

            } catch (e: Exception) {
                runOnUiThread {
                    marsImageView.setImageResource(R.drawable.error_image) // Set error image
                    Log.e("API_Error", "Error en la llamada a la API", e)
                }
            }
        }
    }
}