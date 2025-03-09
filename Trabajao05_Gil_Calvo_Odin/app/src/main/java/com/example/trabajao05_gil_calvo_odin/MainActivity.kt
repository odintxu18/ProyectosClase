package com.example.trabajao05_gil_calvo_odin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View

import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Asocia menús contextuales a las imágenes
        val imgSong1 = findViewById<ImageView>(R.id.imgSong1)
        val imgSong2 = findViewById<ImageView>(R.id.imgSong2)
        val imgSong3 = findViewById<ImageView>(R.id.imgSong3)
        val imgSong4 = findViewById<ImageView>(R.id.imgSong4)

        registerForContextMenu(imgSong1)
        registerForContextMenu(imgSong2)
        registerForContextMenu(imgSong3)
        registerForContextMenu(imgSong4)
    }
    private var currentContextView: View? = null
    // Muestra el menú contextual al mantener pulsado
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        currentContextView = v // Guarda la vista actual
        menuInflater.inflate(R.menu.menu_main, menu)

        when (v?.id) {
            R.id.imgSong1 -> {
                menu?.setHeaderTitle("Cancion Trashi")
                v.tag = "Canción 1: Sin Ti"
            }
            R.id.imgSong2 -> {
                menu?.setHeaderTitle("Cancion Maria Escarmiento")
                v.tag = "Canción 2: Sensacion Carioca"
            }
            R.id.imgSong3 -> {
                menu?.setHeaderTitle("Cancion Belen Aguilera")
                v.tag = "Canción 3: Copiloto"
            }
            R.id.imgSong4 -> {
                menu?.setHeaderTitle("Cancion Jimena Amarillo")
                v.tag = "Canción 4: Cafeliko"
            }
        }
    }

    // Maneja las opciones seleccionadas del menú contextual
    override fun onContextItemSelected(item: MenuItem): Boolean {
        // Recupera la vista seleccionada del menú contextual
        val selectedView = currentContextView // Almacenada en onCreateContextMenu
        val songInfo = selectedView?.tag?.toString() ?: "Información no disponible"

        // Muestra información para depurar
        Toast.makeText(this, "Tag seleccionado: $songInfo", Toast.LENGTH_SHORT).show()

        return when (item.itemId) {
            R.id.action_show_info -> {
                // Mostrar información de la canción
                Toast.makeText(this, songInfo, Toast.LENGTH_LONG).show()
                true
            }

            R.id.action_open_web -> {
                // Abre el enlace de la canción correspondiente
                val url = when (songInfo) {
                    "Canción 1: Sin Ti" -> "https://youtu.be/xN6lfHSHCoo?si=TkpCLry2eqQovYwF"
                    "Canción 2: Sensacion Carioca" -> "https://youtu.be/XNEnpEktVss?si=wl66Y37eadvyYeyH"
                    "Canción 3: Copiloto" -> "https://youtu.be/YYTvvQHZgTQ?si=qmp35YPNtMA6kJOi"
                    "Canción 4: Cafeliko" -> "https://youtu.be/ZmSeGjEu7gE?si=w7zXSWSgaclPBS15"
                    else -> "https://example.com"
                }

                // Depuración de URL seleccionada
                Toast.makeText(this, "URL seleccionada: $url", Toast.LENGTH_SHORT).show()

                // Lanza la actividad con el enlace
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }
}
