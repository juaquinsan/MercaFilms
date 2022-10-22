package com.joaquin.mercafilms.domain

import com.joaquin.mercafilms.data.GhibliRepository
import com.joaquin.mercafilms.data.database.entities.toDatabase
import com.joaquin.mercafilms.domain.models.Film
import javax.inject.Inject

class GetAllFilmsUseCase @Inject constructor (
    private val repository : GhibliRepository
    ) {

    suspend operator fun invoke() : List<Film> {
        var films = repository.getAllFilmsFromDatabase()

        return films.ifEmpty {
            // If database is empty, we need to get film information from API
            films = repository.getAllFilmsFromApi().sortedBy { it.title }
            repository.insertFilms(films.map { it.toDatabase() })
            films
        }
    }
}