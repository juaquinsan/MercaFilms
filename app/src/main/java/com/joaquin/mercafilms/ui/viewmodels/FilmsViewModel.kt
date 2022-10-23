package com.joaquin.mercafilms.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaquin.mercafilms.domain.DeleteAllFilmsUseCase
import com.joaquin.mercafilms.domain.GetAllFilmsUseCase
import com.joaquin.mercafilms.domain.GetFilmByIdUseCase
import com.joaquin.mercafilms.domain.UpdateFilmUseCase
import com.joaquin.mercafilms.domain.models.Film
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor (
    private val getAllFilmsUseCase : GetAllFilmsUseCase,
    private val getFilmByIdUseCase : GetFilmByIdUseCase,
    private val deleteAllFilmsUseCase: DeleteAllFilmsUseCase,
    private val updateFilmUseCase: UpdateFilmUseCase
    ) : ViewModel() {

    /*
        Encapsulate models from LiveData
     */
    val allFilmsModel = MutableLiveData<List<Film>>()
    val filmModel = MutableLiveData<Film>()
    val dataIsLoading = MutableLiveData<Boolean>()

    /*
        Call to All Films use case
     */
    fun onCreate() {
        viewModelScope.launch {
            dataIsLoading.postValue(true)
            val result : List<Film> = getAllFilmsUseCase()

            if (result.isNotEmpty()) {
                allFilmsModel.postValue(result)
                dataIsLoading.postValue(false)
            }
        }
    }

    /*
        Call to one film by ID use case
     */
    fun filmInfo(id : String) {
        viewModelScope.launch {
            dataIsLoading.postValue(true)
            val result : Film = getFilmByIdUseCase(id)

            filmModel.postValue(result)
            dataIsLoading.postValue(false)
        }
    }

    /*
        Delete all films from database
     */
    fun deleteAllFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            dataIsLoading.postValue(true)
            val result : List<Film> = deleteAllFilmsUseCase()

            allFilmsModel.postValue(result)
            dataIsLoading.postValue(false)
        }
    }

    /*
        Update film information
     */
    fun updateFilmInformation(id: String, title: String, original_title: String,
                                      description: String, director: String, producer: String,
                                      release_date: Int, rt_score: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataIsLoading.postValue(true)
            updateFilmUseCase(id, title, original_title, description, director,
                producer, release_date, rt_score)
            dataIsLoading.postValue(false)
        }
    }
}