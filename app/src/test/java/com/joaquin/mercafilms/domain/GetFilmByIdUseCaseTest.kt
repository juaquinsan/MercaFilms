package com.joaquin.mercafilms.domain

import com.joaquin.mercafilms.data.GhibliRepository
import com.joaquin.mercafilms.domain.models.Film
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetFilmByIdUseCaseTest {

    @RelaxedMockK
    private lateinit var ghibliRepository: GhibliRepository

    lateinit var getFilmByIdUseCase: GetFilmByIdUseCase

    @Before
    fun onBefore() {
        /*
           Initialize Mock Library
        */
        MockKAnnotations.init(this)

        getFilmByIdUseCase = GetFilmByIdUseCase(ghibliRepository)
    }

    @Test
    fun `when database is not empty then return film by ID` () = runBlocking {
        // Given
        val film = Film("2baf70d1-42bb-4437-b551-e5fed5a87abe",
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
        coEvery { ghibliRepository.
                getFilmByIdFromDatabase("2baf70d1-42bb-4437-b551-e5fed5a87abe") } returns film

        // When
        val response = getFilmByIdUseCase("2baf70d1-42bb-4437-b551-e5fed5a87abe")

        // Given
        assert(response == film)
    }
}