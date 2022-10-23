package com.joaquin.mercafilms.domain

import com.joaquin.mercafilms.data.GhibliRepository
import com.joaquin.mercafilms.domain.models.Film
import javax.inject.Inject

class DeleteAllFilmsUseCase @Inject constructor (
    private val repository : GhibliRepository
    ) {
        suspend operator fun invoke() : List<Film> {
            repository.clearFilms()
            return emptyList()
        }
}