package com.joaquin.mercafilms.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joaquin.mercafilms.data.database.dao.FilmDao
import com.joaquin.mercafilms.data.database.entities.FilmEntity

@Database(entities = [FilmEntity::class], version = 1)
abstract class FilmDatabase:RoomDatabase() {

    abstract fun getFilmDao():FilmDao
}