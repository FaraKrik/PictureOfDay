package com.example.pictureofdays.main.model.Repository

import com.example.pictureofdays.main.model.Photo
import com.example.pictureofdays.network.INasaApi
import com.example.pictureofdays.network.PhotoResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryImpl: IRepository {

    private const val URL_BASE = "https://api.nasa.gov/"

    override fun photoDay(callback: (result: RepositoryResult<Photo>) -> Unit) {
        val gson = Gson()
        val client = OkHttpClient.Builder().apply {

            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        retrofit.create(INasaApi::class.java).getPhoto()
            .enqueue(
                object :retrofit2.Callback<PhotoResponse> {
                    override fun onResponse(
                        call: Call<PhotoResponse>,
                        response: Response<PhotoResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                val photoDetail: Photo = it.let {
                                    val photoResult: Photo = Photo(
                                        date = it.date,
                                        explanation = it.explanation,
                                        hdUrl = it.hdUrl,
                                        mediaType = it.mediaType,
                                        serviceVersion = it.serviceVersion,
                                        title = it.title,
                                        photoUrl = it.photoUrl
                                    )
                                    photoResult
                                }
                                callback.invoke(Success(photoDetail))
                            }
                        }
                    }
                    override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                        callback.invoke(Error(Exception("")))
                    }
                }
            )
    }
}