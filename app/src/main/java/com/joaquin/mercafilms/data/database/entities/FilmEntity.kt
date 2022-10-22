package com.joaquin.mercafilms.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.joaquin.mercafilms.domain.models.Film

@Entity(tableName = "film_table")
data class FilmEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "original_title") val original_title: String,
    @ColumnInfo(name = "original_title_romanised") val original_title_romanised: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "movie_banner") val movie_banner: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "director") val director: String,
    @ColumnInfo(name = "producer") val producer: String,
    @ColumnInfo(name = "release_date") val release_date: Int,
    @ColumnInfo(name = "running_time") val running_time: Int,
    @ColumnInfo(name = "rt_score") val rt_score: Int,
    @ColumnInfo(name = "url") val url: String
)

fun Film.toDatabase() = FilmEntity(id, title, original_title, original_title_romanised, image,
    movie_banner, description, director, producer, release_date, running_time, rt_score, url)