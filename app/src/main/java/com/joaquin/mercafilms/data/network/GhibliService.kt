package com.joaquin.mercafilms.data.network

import com.joaquin.mercafilms.data.models.FilmApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class GhibliService @Inject constructor (
    private val ghibliAPI : GhibliApiClient
    ) {

    /*
        Retrofit API call all films
     */
    suspend fun getAllFilms() : List<FilmApiResponse> {
        return withContext(Dispatchers.IO) {
            val response : Response<ArrayList<FilmApiResponse>> = ghibliAPI.getAllFilms()
            response.body() ?: emptyList()
        }
    }

    /*
        Retrofit API call film by ID
     */
    suspend fun getFilmById(id: String) : FilmApiResponse {
        return withContext(Dispatchers.IO) {
            val response : Response<FilmApiResponse> = ghibliAPI.getFilmInfoById(id)
            response.body() ?: FilmApiResponse("", "", "",
                "", "", "", "", "",
                "",0, 0, 0, "")
        }
    }
}