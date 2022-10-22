package com.joaquin.mercafilms.domain

import com.joaquin.mercafilms.data.GhibliRepository
import javax.inject.Inject

class UpdateFilmUseCase @Inject constructor (
    private val repository : GhibliRepository
) {
    suspend operator fun invoke(id: String, title: String, original_title: String,
                                description: String, director: String, producer: String,
                                release_date: Int, rt_score: Int) {
        repository.updateFilmFromDatabase(id, title, original_title, description, director,
            producer, release_date, rt_score)
    }
}