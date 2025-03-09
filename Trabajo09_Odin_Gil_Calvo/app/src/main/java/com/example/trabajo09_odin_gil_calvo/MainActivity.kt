package com.example.trabajo09_odin_gil_calvo

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var verContactosButton: Button
    private lateinit var contactosListView: ListView
    private lateinit var nombresContactos: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>

    private val REQUEST_CONTACTS_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verContactosButton = findViewById(R.id.verContactosButton)
        contactosListView = findViewById(R.id.contactosListView)
        nombresContactos = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresContactos)
        contactosListView.adapter = adapter

        verContactosButton.setOnClickListener {
            if (checkContactsPermission()) {
                cargarContactos()
            } else {
                requestContactsPermission()
            }
        }
    }

    private fun checkContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactsPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CONTACTS_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CONTACTS_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de contactos concedido.", Toast.LENGTH_SHORT).show()
                cargarContactos()
            } else {
                Toast.makeText(this, "Permiso de contactos denegado.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarContactos() {
        nombresContactos.clear()
        val contentResolver: ContentResolver = contentResolver
        val uri: Uri = ContactsContract.Contacts.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME
        )
        val selection: String? = null // **MODIFICACIÓN: Eliminar la selección (sin filtro)**
        val selectionArgs: Array<String>? = null // **MODIFICACIÓN: Eliminar los argumentos de selección (sin filtro)**
        val sortOrder: String? = null

        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        cursor?.use { // Usar 'use' para asegurar que el cursor se cierra automáticamente
            if (it.count > 0) {
                while (it.moveToNext()) {
                    val nombre = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                    nombresContactos.add(nombre)
                }
            } else {
                nombresContactos.add("No hay contactos") // Mensaje si no se encuentra
            }
        }
        adapter.notifyDataSetChanged()
    }
}