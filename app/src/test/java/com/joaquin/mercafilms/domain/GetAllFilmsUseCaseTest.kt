package com.joaquin.mercafilms.domain

import com.joaquin.mercafilms.data.GhibliRepository
import com.joaquin.mercafilms.domain.models.Film
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllFilmsUseCaseTest {

    @RelaxedMockK
    private lateinit var ghibliRepository: GhibliRepository

    lateinit var getAllFilmsUseCase: GetAllFilmsUseCase

    @Before
    fun onBefore() {
        /*
            Initialize Mock Library
         */
        MockKAnnotations.init(this)

        getAllFilmsUseCase = GetAllFilmsUseCase(ghibliRepository)
    }

    @Test
    fun `when Ghibli API does not return anything then get values from API` () = runBlocking {
        // Given
        coEvery { ghibliRepository.getAllFilmsFromApi() } returns emptyList()

        // When
        getAllFilmsUseCase()

        // Then
        coVerify(exactly = 1) { ghibliRepository.getAllFilmsFromDatabase() }
    }

    @Test
    fun `when Ghibli API return something then get values from API` () = runBlocking {
        // Given
        val filmsList = listOf(
            Film("2baf70d1-42bb-4437-b551-e5fed5a87abe",
            "Castle in the Sky",
            "天空の城ラピュタ",
            "Tenkū no shiro Rapyuta",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/npOnzAbLh6VOIu3naU5QaEcTepo.jpg",
            "https://image.tmdb.org/t/p/w533_and_h300_bestv2/3cyjYtLWCBE1uvWINHFsFnE8LUK.jpg",
            "The orphan Sheeta inherited a mysterious crystal that links her to the mythical sky-kingdom of Laputa. With the help of resourceful Pazu and a rollicking band of sky pirates, she makes her way to the ruins of the once-great civilization. Sheeta and Pazu must outwit the evil Muska, who plans to use Laputa's science to make himself ruler of the world.",
            "Hayao Miyazaki",
            "Isao Takahata",
            1986,
            124,
            95,
            "https://ghibliapi.herokuapp.com/films/2baf70d1-42bb-4437-b551-e5fed5a87abe")
        )
        coEvery { ghibliRepository.getAllFilmsFromApi() } returns filmsList

        // When
        val response = getAllFilmsUseCase()

        // Then
        coVerify(exactly = 1) { ghibliRepository.insertFilms(any()) }
        coVerify(exactly = 1) { ghibliRepository.getAllFilmsFromDatabase() }
        assert(response == filmsList)
    }

    @Test
    fun `when database has information about API films then get values from database` () = runBlocking {
        // Given
        val filmsList = listOf(
            Film("2baf70d1-42bb-4437-b551-e5fed5a87abe",
                "Castle in the Sky",
                "天空の城ラピュタ",
                "Tenkū no shiro Rapyuta",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/npOnzAbLh6VOIu3naU5QaEcTepo.jpg",
                "https://image.tmdb.org/t/p/w533_and_h300_bestv2/3cyjYtLWCBE1uvWINHFsFnE8LUK.jpg",
                "The orphan Sheeta inherited a mysterious crystal that links her to the mythical sky-kingdom of Laputa. With the help of resourceful Pazu and a rollicking band of sky pirates, she makes her way to the ruins of the once-great civilization. Sheeta and Pazu must outwit the evil Muska, who plans to use Laputa's science to make himself ruler of the world.",
                "Hayao Miyazaki",
                "Isao Takahata",
                1986,
                124,
                95,
                "https://ghibliapi.herokuapp.com/films/2baf70d1-42bb-4437-b551-e5fed5a87abe")
        )
        coEvery { ghibliRepository.getAllFilmsFromDatabase() } returns filmsList

        // When
        val response = getAllFilmsUseCase()

        // Then
        assert(response == filmsList)
    }
}