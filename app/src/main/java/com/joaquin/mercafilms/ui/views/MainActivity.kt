package com.joaquin.mercafilms.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaquin.mercafilms.R
import com.joaquin.mercafilms.databinding.ActivityMainBinding
import com.joaquin.mercafilms.domain.adapters.FilmsAdapter
import com.joaquin.mercafilms.domain.iterfaces.RecyclerViewFilmListener
import com.joaquin.mercafilms.domain.models.Film
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

        setSupportActionBar(binding.toolbar.root)

        filmsViewModel.onCreate()

        filmsViewModel.allFilmsModel.observe(this) {
            if (it.isNotEmpty()) {
                // They are films to show
                binding.recyclerViewFilms.visibility = View.VISIBLE
                binding.layoutNoFilms.visibility = View.GONE

                binding.recyclerViewFilms.setHasFixedSize(true)
                binding.recyclerViewFilms.itemAnimator = DefaultItemAnimator()
                binding.recyclerViewFilms.layoutManager = LinearLayoutManager(this)

                val filmsAdapter : FilmsAdapter = FilmsAdapter(it,
                    object : RecyclerViewFilmListener {
                        override fun onClick(film: Film, position: Int) {
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
        Menu options
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete_all -> {
                filmsViewModel.deleteAllFilms()
                true
            }
            R.id.menu_update -> {
                filmsViewModel.deleteAllFilms()
                filmsViewModel.onCreate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*
        Go to DetailActivity passing film information selected (ID)
     */
    private fun goToDetailActivity(filmID: String) {
        if (filmID != "") {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("filmid", filmID)
            startActivity(intent)
            finish()
        }
    }
}