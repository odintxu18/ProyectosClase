package api


import com.example.trabajo11_gil_calvo_odin.curiosity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsPhotos(
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String
    ): Call<curiosity>
}