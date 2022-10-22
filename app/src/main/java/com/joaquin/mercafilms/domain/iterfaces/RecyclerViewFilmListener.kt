package com.joaquin.mercafilms.domain.iterfaces

import com.joaquin.mercafilms.domain.models.Film

/*
    Interface recyclerview film list
 */
interface RecyclerViewFilmListener {
    fun onClick(film: Film, position: Int)
}