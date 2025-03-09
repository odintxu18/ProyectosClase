package com.example.gil_calvo_odin_pmdm12_tarea

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var cityName: String
    private var cityLatitude: Double = 0.0
    private var cityLongitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Obtener datos del Intent
        cityName = intent.getStringExtra("CITY_NAME") ?: ""
        cityLatitude = intent.getDoubleExtra("CITY_LATITUDE", 0.0)
        cityLongitude = intent.getDoubleExtra("CITY_LONGITUDE", 0.0)

        // Obtener el SupportMapFragment y configurar el callback para cuando el mapa esté listo
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Crear un objeto LatLng con las coordenadas de la ciudad
        val cityLocation = LatLng(cityLatitude, cityLongitude)

        // Añadir un marcador en la ubicación de la ciudad
        mMap.addMarker(MarkerOptions().position(cityLocation).title(cityName))

        // Mover la cámara del mapa a la ubicación de la ciudad y hacer zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, 12f)) // Zoom level 12 (puedes ajustarlo)
    }
}