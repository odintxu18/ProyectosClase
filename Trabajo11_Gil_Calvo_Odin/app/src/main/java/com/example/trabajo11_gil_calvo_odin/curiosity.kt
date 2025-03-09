package com.example.trabajo11_gil_calvo_odin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class curiosity {
    @SerializedName("photos")
    @Expose
    var photos: List<Photo>? = null