package com.joaquin.mercafilms.domain

import com.joaquin.mercafilms.data.GhibliRepository
import com.joaquin.mercafilms.data.models.FilmApiResponse
import javax.inject.Inject

class GetFilmByIdUseCase @Inject constructor(
    private val repository: GhibliRepository
) {
    suspend operator fun invoke(id: String) : FilmApiResponse = repository.getFilmById(id)
}