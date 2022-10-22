package com.joaquin.mercafilms.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaquin.mercafilms.data.adapters.FilmsAdapter
import com.joaquin.mercafilms.data.adapters.iterfaces.RecyclerViewFilmListener
import com.joaquin.mercafilms.data.models.FilmApiResponse
import com.joaquin.mercafilms.databinding.ActivityMainBinding
import com.joaquin.mercafilms.ui.viewmodels.FilmsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val filmsViewModel : FilmsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filmsViewModel.onCreate()

        filmsViewModel.allFilmsModel.observe(this) {

            if (it.size > 0) {
                // They are films to show
                binding.recyclerViewFilms.visibility = View.VISIBLE
                binding.layoutNoFilms.visibility = View.GONE

                binding.recyclerViewFilms.setHasFixedSize(true)
                binding.recyclerViewFilms.itemAnimator = DefaultItemAnimator()
                binding.recyclerViewFilms.layoutManager = LinearLayoutManager(this)

                val filmsAdapter : FilmsAdapter = FilmsAdapter(it,
                    object : RecyclerViewFilmListener {
                        override fun onClick(film: FilmApiResponse, position: Int) {
                            goToDetailActivity(film.id)
                        }
                    })

                binding.recyclerViewFilms.adapter = filmsAdapter
            } else {
                // There aren't films to show
                binding.recyclerViewFilms.visibility = View.GONE
                binding.layoutNoFilms.visibility = View.VISIBLE
            }
        }

        /*
            Progressbar update
         */
        filmsViewModel.dataIsLoading.observe(this) {
            binding.progressBarLoadingContent.isVisible = it
        }
    }

    /*
        Go to DetailActivity passing film information selected (ID)
     */
    private fun goToDetailActivity(filmID: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("filmid", filmID)
        startActivity(intent)
        finish()
    }
}