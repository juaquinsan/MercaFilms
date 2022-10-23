package com.joaquin.mercafilms.domain

import com.joaquin.mercafilms.data.GhibliRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteAllFilmsUseCaseTest {

    @RelaxedMockK
    private lateinit var ghibliRepository: GhibliRepository

    lateinit var deleteAllFilmsUseCase: DeleteAllFilmsUseCase

    @Before
    fun onBefore() {
        /*
           Initialize Mock Library
        */
        MockKAnnotations.init(this)

        deleteAllFilmsUseCase = DeleteAllFilmsUseCase(ghibliRepository)
    }

    @Test
    fun `when database is not empty then clear all films` () = runBlocking {
        // Given
        coEvery { ghibliRepository.clearFilms() } returns emptyList()

        // When
        var response = deleteAllFilmsUseCase()

        // Then
        assert(response.isEmpty())
    }
}