package com.joaquin.mercafilms.data.models

import com.google.gson.annotations.SerializedName

data class FilmApiResponse (
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("original_title_romanised") val original_title_romanised: String,
    @SerializedName("image") val image: String,
    @SerializedName("movie_banner") val movie_banner: String,
    @SerializedName("description") val description: String,
    @SerializedName("director") val director: String,
    @SerializedName("producer") val producer: String,
    @SerializedName("release_date") val release_date: Int,
    @SerializedName("running_time") val running_time: Int,
    @SerializedName("rt_score") val rt_score: Int,
    @SerializedName("url") val url: String
)