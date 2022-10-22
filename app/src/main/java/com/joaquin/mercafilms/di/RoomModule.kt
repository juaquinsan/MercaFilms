package com.joaquin.mercafilms.di

import android.content.Context
import androidx.room.Room
import com.joaquin.mercafilms.data.database.FilmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val FILM_DATABASE_NAME = "film_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        FilmDatabase::class.java, FILM_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideFilmDao(db: FilmDatabase) = db.getFilmDao()
}