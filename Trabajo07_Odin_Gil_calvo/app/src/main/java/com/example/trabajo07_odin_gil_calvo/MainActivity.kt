package com.example.trabajo07_odin_gil_calvo



import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var randomNumber = 0
    private lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("GamePreferences", Context.MODE_PRIVATE)

        // Recuperar la puntuación acumulada
        val currentScore = sharedPreferences.getInt("score", 0)

        // Referencias a los elementos de la interfaz
        val scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        val inputNumber = findViewById<EditText>(R.id.numberInput)
        val checkButton = findViewById<Button>(R.id.checkButton)

        // Mostrar puntuación actual
        scoreTextView.text = "Puntuación: $currentScore"

        // Generar número aleatorio
        generateRandomNumber()

        // Botón para comprobar el número
        checkButton.setOnClickListener {
            val userInput = inputNumber.text.toString()

            if (userInput.isNotEmpty()) {
                val guessedNumber = userInput.toInt()

                when {
                    guessedNumber > randomNumber -> {
                        Toast.makeText(this, "El número es menor", Toast.LENGTH_SHORT).show()
                    }
                    guessedNumber < randomNumber -> {
                        Toast.makeText(this, "El número es mayor", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "¡Has acertado!", Toast.LENGTH_SHORT).show()

                        // Incrementar la puntuación
                        val newScore = currentScore + 1
                        sharedPreferences.edit().putInt("score", newScore).apply()

                        // Actualizar puntuación en la interfaz
                        scoreTextView.text = "Puntuación: $newScore"

                        // Generar un nuevo número
                        generateRandomNumber()
                    }
                }
            } else {
                Toast.makeText(this, "Introduce un número válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para generar un número aleatorio entre 1 y 20
    private fun generateRandomNumber() {
        randomNumber = Random.nextInt(1, 21)
    }
}
