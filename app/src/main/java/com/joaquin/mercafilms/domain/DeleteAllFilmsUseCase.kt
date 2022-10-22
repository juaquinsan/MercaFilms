package com.joaquin.mercafilms.domain

import com.joaquin.mercafilms.data.GhibliRepository
import javax.inject.Inject

class DeleteAllFilmsUseCase @Inject constructor (
    private val repository : GhibliRepository
    ) {
        suspend operator fun invoke() {
            repository.clearFilms()
        }
}