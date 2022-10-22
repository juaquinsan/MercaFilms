package com.joaquin.mercafilms.domain.models

import com.joaquin.mercafilms.data.database.entities.FilmEntity
import com.joaquin.mercafilms.data.models.FilmApiResponse

data class Film (
    val id: String,
    val title: String,
    val original_title: String,
    val original_title_romanised: String,
    val image: String,
    val movie_banner: String,
    val description: String,
    val director: String,
    val producer: String,
    val release_date: Int,
    val running_time: Int,
    val rt_score: Int,
    val url: String
)

fun FilmApiResponse.toDomain() = Film(id, title,
 original_title, original_title_romanised, image, movie_banner, description, director, producer,
 release_date, running_time, rt_score, url)

fun FilmEntity.toDomain() = Film(id, title,
    original_title, original_title_romanised, image, movie_banner, description, director, producer,
    release_date, running_time, rt_score, url)