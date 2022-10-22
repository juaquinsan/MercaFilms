package com.joaquin.mercafilms.domain.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joaquin.mercafilms.R
import com.joaquin.mercafilms.domain.iterfaces.RecyclerViewFilmListener
import com.joaquin.mercafilms.domain.models.Film
import com.joaquin.mercafilms.utils.inflate
import com.joaquin.mercafilms.utils.loadByUrl

class FilmsAdapter (private val films: List<Film>,
                    private val listener: RecyclerViewFilmListener) :
    RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.card_film))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(films[position], listener)
    }

    override fun getItemCount(): Int = films.size

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind (film: Film,
                  listener: RecyclerViewFilmListener
        ) = with(itemView) {
            val imageViewFilm = findViewById<ImageView>(R.id.imageViewFilm)
            val textViewTitle = findViewById<TextView>(R.id.textViewFilTitle)
            val textViewDescription = findViewById<TextView>(R.id.textViewFilmDescription)

            imageViewFilm.loadByUrl(film.image)

            textViewTitle.text = film.title
            textViewDescription.text = film.description

            setOnClickListener { listener.onClick(film, adapterPosition) }
        }
    }
}