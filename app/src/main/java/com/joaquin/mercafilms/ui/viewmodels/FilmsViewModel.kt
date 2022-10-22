package com.joaquin.mercafilms.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaquin.mercafilms.data.models.FilmApiResponse
import com.joaquin.mercafilms.domain.GetAllFilmsUseCase
import com.joaquin.mercafilms.domain.GetFilmByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor (
    private val getAllFilmsUseCase : GetAllFilmsUseCase,
    private val getFilmByIdUseCase : GetFilmByIdUseCase
    ) : ViewModel() {

    /*
        Encapsulate models from LiveData
     */
    val allFilmsModel = MutableLiveData<ArrayList<FilmApiResponse>>()
    val filmModel = MutableLiveData<FilmApiResponse>()
    val dataIsLoading = MutableLiveData<Boolean>()

    /*
        Call to All Films use case
     */
    fun onCreate() {
        viewModelScope.launch {
            dataIsLoading.postValue(true)
            val result : ArrayList<FilmApiResponse> = getAllFilmsUseCase()

            allFilmsModel.postValue(result)
            dataIsLoading.postValue(false)
        }
    }

    /*
        Call to one film by ID use case
     */
    fun filmInfo(id : String) {
        viewModelScope.launch {
            dataIsLoading.postValue(true)
            val result : FilmApiResponse = getFilmByIdUseCase(id)

            filmModel.postValue(result)
            dataIsLoading.postValue(false)
        }
    }
}