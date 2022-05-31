package com.example.pictureofdays.network


import retrofit2.Call
import retrofit2.http.GET

interface INasaApi {

    @GET("https://api.nasa.gov/planetary/apod?api_key=u9P7YrtdPRJBtp3FGy4sSzVJVMFU5AZ6nph2HWvk")
    fun getPhoto(
    ): Call<PhotoResponse>
}