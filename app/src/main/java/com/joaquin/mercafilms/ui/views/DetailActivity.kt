package com.joaquin.mercafilms.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.joaquin.mercafilms.R
import com.joaquin.mercafilms.databinding.ActivityDetailBinding
import com.joaquin.mercafilms.ui.viewmodels.FilmsViewModel
import com.joaquin.mercafilms.utils.goToActivity
import com.joaquin.mercafilms.utils.loadByUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    private val filmsViewModel : FilmsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
            Get film selected from intent (ID)
         */
        val filmID = intent.getStringExtra("filmid")

        if (filmID != null) {
            filmsViewModel.filmInfo(filmID)
        }

        filmsViewModel.filmModel.observe(this) {

            if (it != null) {
                // There are information to show
                binding.layoutFilmInfo.visibility = View.VISIBLE
                binding.layoutNoFilmInfo.visibility = View.GONE

                binding.imageViewFilm.loadByUrl(it.image)
                binding.textViewTitle.text = it.title
                binding.textViewOriginalTitle.text = it.original_title
                binding.textViewDirector.text = it.director
                binding.textViewProducer.text = it.producer
                binding.textViewReleaseDate.text = "${it.release_date}"
                binding.textViewScore.text = "${it.rt_score}/100"

                binding.textViewFilmDescription.text = it.description

            } else {
                // An error occurs to show film's info
                binding.layoutFilmInfo.visibility = View.GONE
                binding.layoutNoFilmInfo.visibility = View.VISIBLE
            }
        }

        filmsViewModel.dataIsLoading.observe(this) {
            binding.progressBarLoadingContent.isVisible = it
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goToActivity<MainActivity>()
    }
}