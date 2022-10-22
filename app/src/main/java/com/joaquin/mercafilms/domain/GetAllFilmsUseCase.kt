package com.joaquin.mercafilms.domain

import com.joaquin.mercafilms.data.GhibliRepository
import com.joaquin.mercafilms.data.models.FilmApiResponse
import javax.inject.Inject

class GetAllFilmsUseCase @Inject constructor (
    private val repository : GhibliRepository
    ) {

    suspend operator fun invoke() : ArrayList<FilmApiResponse> = repository.getAllFilms()
}