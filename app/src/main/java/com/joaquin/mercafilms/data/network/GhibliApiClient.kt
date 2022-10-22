package com.joaquin.mercafilms.data.network

import com.joaquin.mercafilms.data.models.FilmApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GhibliApiClient {

    /*
        Return all films from Ghibli API
     */
    @GET("films")
    suspend fun getAllFilms(): Response<ArrayList<FilmApiResponse>>

    /*
        Return all film information by film ID
     */
    @GET("films/{id}")
    suspend fun getFilmInfoById(@Path("id") id: String): Response<FilmApiResponse>
}