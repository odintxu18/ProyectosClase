package com.example.gil_calvo_odin_pmdm12_tarea

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity



import android.widget.ListView


class MainActivity : AppCompatActivity() {

    private lateinit var cityListView: ListView
    private lateinit var cityList: List<City>
    private lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityListView = findViewById(R.id.cityListView)
        cityList = createCityList()
        cityAdapter = CityAdapter(this, cityList)
        cityListView.adapter = cityAdapter

    }

    private fun createCityList(): List<City> {
        val cities = ArrayList<City>()

        cities.add(City("Bruselas", "Bélgica", "177 307", R.drawable.bruselas, 50.8467, 4.3547))
        cities.add(City("Budapest", "Hungría", "1 752 704", R.drawable.budapest, 47.498333, 19.040833))
        cities.add(City("Dublín", "Irlanda", "527 612", R.drawable.dublin, 53.3425, -6.265833))
        cities.add(City("Florencia", "Italia", "382 258", R.drawable.florencia, 43.771389, 11.254167))
        cities.add(City("París", "Francia", "10 516 110", R.drawable.paris, 48.856578, 2.351828))
        cities.add(City("Praga", "Chequia", "1 280 508", R.drawable.praga, 50.088611, 14.421389))
        cities.add(City("Sevilla", "España", "689 434", R.drawable.sevilla, 37.383333, -5.983333))
        cities.add(City("Viena", "Austria", "1 840 573", R.drawable.viena, 48.20833, 16.373064))

        return cities
    }


    private fun startMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
}