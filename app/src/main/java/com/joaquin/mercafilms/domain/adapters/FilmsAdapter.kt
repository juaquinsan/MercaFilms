package com.joaquin.mercafilms.domain.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joaquin.mercafilms.R
import com.joaquin.mercafilms.databinding.CardFilmBinding
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
            val binding = CardFilmBinding.bind(itemView)

            binding.imageViewFilm.loadByUrl(film.image)

            binding.textViewFilTitle.text = film.title
            binding.textViewFilmDescription.text = film.description

            /*
                Set maxLines to textview description depend to maxheight
             */
            binding.textViewFilmDescription.viewTreeObserver.addOnGlobalLayoutListener {
                val numberLinesVisibles = binding.textViewFilmDescription.height /
                        binding.textViewFilmDescription.lineHeight
                binding.textViewFilmDescription.maxLines = numberLinesVisibles
            }

            setOnClickListener { listener.onClick(film, adapterPosition) }
        }
    }
}