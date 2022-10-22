package com.joaquin.mercafilms.data.database.dao

import androidx.room.*
import com.joaquin.mercafilms.data.database.entities.FilmEntity
import com.joaquin.mercafilms.domain.models.Film

@Dao
interface FilmDao {

    @Query("SELECT * FROM film_table ORDER BY title ASC")
    suspend fun getAllFilms(): List<FilmEntity>

    @Query("SELECT * FROM film_table WHERE id IN (:id)")
    suspend fun getFilmById(id: String): Film

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(films:List<FilmEntity>)

    @Query("UPDATE film_table SET title = (:title), original_title = (:original_title), " +
            "description = (:description), director = (:director), producer = (:producer)," +
            " release_date = (:release_date), rt_score = (:rt_score) WHERE id = (:id)")
    suspend fun updateFilm(id: String, title: String, original_title: String,
                    description: String, director: String, producer: String, release_date: Int,
                    rt_score: Int)

    @Query("DELETE FROM film_table")
    suspend fun deleteAllFilms()
}