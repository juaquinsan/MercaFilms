package com.joaquin.mercafilms.data

import com.joaquin.mercafilms.data.models.FilmApiResponse
import com.joaquin.mercafilms.data.network.GhibliService
import javax.inject.Inject

class GhibliRepository @Inject constructor (
    private val api : GhibliService
    ) {

    suspend fun getAllFilms(): ArrayList<FilmApiResponse> {
        return api.getAllFilms()
    }

    suspend fun getFilmById(id : String) : FilmApiResponse {
        return api.getFilmById(id)
    }
}