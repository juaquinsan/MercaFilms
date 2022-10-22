package com.joaquin.mercafilms.data.adapters.iterfaces

import com.joaquin.mercafilms.data.models.FilmApiResponse
import java.text.FieldPosition

/*
    Interface recyclerview film list
 */
interface RecyclerViewFilmListener {
    fun onClick(film: FilmApiResponse, position: Int)
}