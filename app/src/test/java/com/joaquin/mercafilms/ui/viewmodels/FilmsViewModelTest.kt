package com.joaquin.mercafilms.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.joaquin.mercafilms.domain.DeleteAllFilmsUseCase
import com.joaquin.mercafilms.domain.GetAllFilmsUseCase
import com.joaquin.mercafilms.domain.GetFilmByIdUseCase
import com.joaquin.mercafilms.domain.UpdateFilmUseCase
import com.joaquin.mercafilms.domain.models.Film
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FilmsViewModelTest {

    @RelaxedMockK
    private lateinit var getAllFilmsUseCase: GetAllFilmsUseCase
    @RelaxedMockK
    private lateinit var getFilmByIdUseCase: GetFilmByIdUseCase
    @RelaxedMockK
    private lateinit var deleteAllFilmsUseCase: DeleteAllFilmsUseCase
    @RelaxedMockK
    private lateinit var updateFilmUseCase: UpdateFilmUseCase

    private lateinit var filmsViewModel: FilmsViewModel

    @get:Rule
    var rule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        /*
            Intialize Mock library
         */
        MockKAnnotations.init(this)

        filmsViewModel = FilmsViewModel(getAllFilmsUseCase, getFilmByIdUseCase,
            deleteAllFilmsUseCase, updateFilmUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when FilmsViewModel is created at first time, get all films and set the variable` () = runTest {
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
        coEvery { getAllFilmsUseCase() } returns filmsList

        // When
        filmsViewModel.onCreate()

        // Then
        assert(filmsViewModel.allFilmsModel.value == filmsList)
    }

    @Test
    fun `when FilmsViewModel filmInfoById return a film by ID from database on the livedata` () = runTest {
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

        coEvery { getFilmByIdUseCase("2baf70d1-42bb-4437-b551-e5fed5a87abe") } returns film

        // When
        filmsViewModel.filmInfo("2baf70d1-42bb-4437-b551-e5fed5a87abe")

        // Then
        assert(filmsViewModel.filmModel.value == film)
    }
}