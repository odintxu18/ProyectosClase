package com.example.trabajo11_gil_calvo_odin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rover {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("landing_date")
    @Expose
    var landingDate: String? = null

    @SerializedName("launch_date")
    @Expose
    var launchDate: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null
}