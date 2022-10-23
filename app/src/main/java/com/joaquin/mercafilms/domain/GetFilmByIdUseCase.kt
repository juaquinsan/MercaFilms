package com.joaquin.mercafilms.domain

import com.joaquin.mercafilms.data.GhibliRepository
import com.joaquin.mercafilms.data.database.entities.toDatabase
import com.joaquin.mercafilms.domain.models.Film
import javax.inject.Inject

class GetFilmByIdUseCase @Inject constructor(
    private val repository: GhibliRepository
) {
    suspend operator fun invoke(id: String) : Film {
        return repository.getFilmByIdFromDatabase(id)
    }
}