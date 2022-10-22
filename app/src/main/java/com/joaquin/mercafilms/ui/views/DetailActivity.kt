package com.joaquin.mercafilms.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
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

    private lateinit var menu : Menu
    private lateinit var filmID : String
    private var isEditingMode : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.root)

        /*
            Get film selected from intent (ID)
         */
        filmID = intent.getStringExtra("filmid").toString()

        filmsViewModel.filmInfo(filmID)

        filmsViewModel.filmModel.observe(this) {

            if (it != null) {
                // There are information to show
                binding.layoutFilmInfo.visibility = View.VISIBLE
                binding.layoutNoFilmInfo.visibility = View.GONE

                binding.imageViewFilm.loadByUrl(it.image)
                binding.textViewTitle.text = it.title
                binding.editTextTitle.setText(it.title)
                binding.textViewOriginalTitle.text = it.original_title
                binding.editTextOriginalTitle.setText(it.original_title)
                binding.textViewDirector.text = it.director
                binding.editTextDirector.setText(it.director)
                binding.textViewProducer.text = it.producer
                binding.editTextProducer.setText(it.producer)
                binding.textViewReleaseDate.text = "${it.release_date}"
                binding.editTextReleaseDate.setText("${it.release_date}")
                binding.textViewScore.text = "${it.rt_score}/100"
                binding.editTextScore.setText("${it.rt_score}".split("/")[0])
                binding.textViewFilmDescription.text = it.description
                binding.editTextDescription.setText(it.description)
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

    /*
        Menu options
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            this.menu = menu
        }
        menuInflater.inflate(R.menu.menu_detail_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_edit_information -> {
                isEditingMode = true
                // Set visibility to item menu
                // Edit menu
                menu[0].isVisible = false
                // Confirm menu
                menu[1].isVisible = true
                menu[2].isVisible = true

                // Show edit warning information
                binding.textViewEditInformationMessage.visibility = View.VISIBLE

                startEditFields()
                true
            }
            R.id.menu_save_changes -> {
                isEditingMode = false
                // Hide edit warning information
                binding.textViewEditInformationMessage.visibility = View.GONE
                // Set visitiblity to item menu
                // Edit menu
                menu[0].isVisible = true
                // Confirm menu
                menu[1].isVisible = false
                menu[2].isVisible = false

                // Update fields
                binding.textViewTitle.text = binding.editTextTitle.text.toString()
                binding.textViewOriginalTitle.text = binding.editTextOriginalTitle.text.toString()
                binding.textViewDirector.text = binding.editTextDirector.text.toString()
                binding.textViewProducer.text = binding.editTextProducer.text.toString()
                binding.textViewReleaseDate.text = binding.editTextReleaseDate.text.toString()
                binding.textViewScore.text =  binding.editTextScore.text.toString() + "/100"
                binding.textViewFilmDescription.text = binding.editTextDescription.text.toString()

                // Update database
                filmsViewModel.updateFilmInformation(
                    filmID,
                    binding.textViewTitle.text.toString(),
                    binding.textViewOriginalTitle.text.toString(),
                    binding.textViewFilmDescription.text.toString(),
                    binding.textViewDirector.text.toString(),
                    binding.textViewProducer.text.toString(),
                    binding.textViewReleaseDate.text.toString().toInt(),
                    binding.textViewScore.text.toString().split("/")[0].toInt()
                )

                finishEditFields()
                true
            }
            R.id.menu_discard_changes -> {
                isEditingMode = false
                // Hide edit warning information
                binding.textViewEditInformationMessage.visibility = View.GONE
                // Set visitiblity to item menu
                // Edit menu
                menu[0].isVisible = true
                // Confirm menu
                menu[1].isVisible = false
                menu[2].isVisible = false

                // Set all textview information into edittext ("Reset" information)
                binding.editTextTitle.setText(binding.textViewTitle.text)
                binding.editTextOriginalTitle.setText(binding.textViewOriginalTitle.text)
                binding.editTextDirector.setText(binding.textViewDirector.text)
                binding.editTextProducer.setText(binding.textViewProducer.text)
                binding.editTextReleaseDate.setText(binding.textViewReleaseDate.text)
                binding.editTextScore.setText(binding.textViewScore.text)
                binding.editTextDescription.setText(binding.textViewFilmDescription.text)

                finishEditFields()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*
        Manage visibility from fields
     */
    fun startEditFields() {
        // Hide textview and show edittext
        binding.textViewTitle.visibility = View.INVISIBLE
        binding.editTextTitle.visibility = View.VISIBLE

        binding.textViewOriginalTitle.visibility = View.GONE
        binding.editTextOriginalTitle.visibility = View.VISIBLE

        binding.textViewDirector.visibility = View.GONE
        binding.editTextDirector.visibility = View.VISIBLE

        binding.textViewProducer.visibility = View.GONE
        binding.editTextProducer.visibility = View.VISIBLE

        binding.textViewReleaseDate.visibility = View.GONE
        binding.editTextReleaseDate.visibility = View.VISIBLE

        binding.textViewScore.visibility = View.GONE
        binding.editTextScore.visibility = View.VISIBLE

        binding.textViewFilmDescription.visibility = View.GONE
        binding.editTextDescription.visibility = View.VISIBLE
    }

    fun finishEditFields() {
        // Hide all edittext and shows the textview
        binding.textViewTitle.visibility = View.VISIBLE
        binding.editTextTitle.visibility = View.GONE

        binding.textViewOriginalTitle.visibility = View.VISIBLE
        binding.editTextOriginalTitle.visibility = View.GONE

        binding.textViewDirector.visibility = View.VISIBLE
        binding.editTextDirector.visibility = View.GONE

        binding.textViewProducer.visibility = View.VISIBLE
        binding.editTextProducer.visibility = View.GONE

        binding.textViewReleaseDate.visibility = View.VISIBLE
        binding.editTextReleaseDate.visibility = View.GONE

        binding.textViewScore.visibility = View.VISIBLE
        binding.editTextScore.visibility = View.GONE

        binding.textViewFilmDescription.visibility = View.VISIBLE
        binding.editTextDescription.visibility = View.GONE
    }

    private fun showConfirmAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.alertDialogEditTitle)
        builder.setMessage(R.string.alertDialogEditMessage)

        builder.setPositiveButton(R.string.alertDialogEditPossitiveButton) { dialog, which ->
            goToActivity<MainActivity>()
            finish()
        }

        builder.setNegativeButton(R.string.alertDialogEditNegativeButton) { dialog, which ->
        }

        builder.show()
    }

    override fun onBackPressed() {
        if (!isEditingMode) {
            goToActivity<MainActivity>()
            finish()
        } else {
            showConfirmAlertDialog()
        }
    }
}