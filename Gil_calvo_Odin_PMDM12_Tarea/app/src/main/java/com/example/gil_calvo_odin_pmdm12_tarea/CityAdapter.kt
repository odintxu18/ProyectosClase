package com.example.gil_calvo_odin_pmdm12_tarea


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.app.Dialog // Importa la clase Dialog de android.app

class CityAdapter(context: Context, private val cities: List<City>) :
    ArrayAdapter<City>(context, 0, cities) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_city, parent, false)
        }

        val currentCity = cities[position]

        val cityImageView = listItemView!!.findViewById<ImageView>(R.id.cityImageView)
        val cityNameTextView = listItemView.findViewById<TextView>(R.id.cityNameTextView)
        val cityDetailsTextView = listItemView.findViewById<TextView>(R.id.cityDetailsTextView)
        val expandImageView = listItemView.findViewById<ImageView>(R.id.expandImageView)
        val locationImageView = listItemView.findViewById<ImageView>(R.id.locationImageView)

        cityImageView.setImageResource(currentCity.imageResource)
        cityNameTextView.text = currentCity.name
        cityDetailsTextView.text = "${currentCity.country}, Población: ${currentCity.population}"

        // OnClickListener para el icono de Expandir Imagen
        expandImageView.setOnClickListener {
            showExpandedImageDialog(currentCity)
        }

        // OnClickListener para el icono de Geolocalización
        locationImageView.setOnClickListener {
            openMapActivity(currentCity)
        }

        return listItemView!!
    }


    private fun showExpandedImageDialog(city: City) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.city_image_dialog)
        val imageView = dialog.findViewById<ImageView>(R.id.dialogCityImageView)
        imageView.setImageResource(city.imageResource)
        dialog.show()
    }

    private fun openMapActivity(city: City) {
        val mapIntent = Intent(context, MapActivity::class.java)
        mapIntent.putExtra("CITY_NAME", city.name)
        mapIntent.putExtra("CITY_LATITUDE", city.latitude)
        mapIntent.putExtra("CITY_LONGITUDE", city.longitude)
        context.startActivity(mapIntent)
    }
}