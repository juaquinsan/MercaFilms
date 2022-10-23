package com.joaquin.mercafilms.data

import com.joaquin.mercafilms.data.database.dao.FilmDao
import com.joaquin.mercafilms.data.database.entities.FilmEntity
import com.joaquin.mercafilms.data.models.FilmApiResponse
import com.joaquin.mercafilms.data.network.GhibliService
import com.joaquin.mercafilms.domain.models.Film
import com.joaquin.mercafilms.domain.models.toDomain
import javax.inject.Inject

class GhibliRepository @Inject constructor (
    private val api : GhibliService,
    private val filmDao: FilmDao
    ) {

    suspend fun getAllFilmsFromApi() : List<Film> {
        val response : List<FilmApiResponse> = api.getAllFilms()
        return response.map { it.toDomain() }
    }

    suspend fun getFilmByIdFromApi(id : String) : Film {
        return api.getFilmById(id).toDomain()
    }

    suspend fun getAllFilmsFromDatabase() : List<Film> {
        val response = filmDao.getAllFilms()
        return response.map { it.toDomain() }
    }

    suspend fun getFilmByIdFromDatabase(id : String) : Film {
        return filmDao.getFilmById(id)
    }

    suspend fun insertFilms(films : List<FilmEntity>) {
        filmDao.insertAll(films)
    }

    suspend fun clearFilms() : List<Film> {
        filmDao.deleteAllFilms()
        return emptyList()
    }

    suspend fun deleteFilmByIdToDatabase(id : String) {
        filmDao.deleteFilmById(id)
    }

    suspend fun updateFilmToDatabase(id: String, title: String, original_title: String,
                                       description: String, director: String, producer: String,
                                       release_date: Int, rt_score: Int) {
        filmDao.updateFilm(id, title, original_title, description, director, producer, release_date,
            rt_score)
    }
}