package com.example.trabajo11_gil_calvo_odin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Camera {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("rover_id")
    @Expose
    var roverId: Int? = null

    @SerializedName("full_name")
    @Expose
    var fullName: String? = null
}