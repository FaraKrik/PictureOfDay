package com.example.pictureofdays.network

import com.google.gson.annotations.SerializedName

data class PhotoResponse (
    @SerializedName("date")
    val date: String?,

    @SerializedName("explanation")
    val explanation: String?,

    @SerializedName("hdurl")
    val hdUrl: String?,

    @SerializedName("media_type")
    val mediaType: String?,

    @SerializedName("service_version")
    val serviceVersion: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("url")
    val photoUrl: String?,
)