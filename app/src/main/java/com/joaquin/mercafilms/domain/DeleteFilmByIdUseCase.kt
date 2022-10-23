package com.joaquin.mercafilms.domain

import com.joaquin.mercafilms.data.GhibliRepository
import javax.inject.Inject

class DeleteFilmByIdUseCase @Inject constructor (
    private val repository : GhibliRepository
) {
    suspend operator fun invoke (id: String) {
        repository.deleteFilmByIdToDatabase(id)
    }
}