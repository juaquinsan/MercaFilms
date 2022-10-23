package com.joaquin.mercafilms.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
            Check editing mode
         */
        if (savedInstanceState != null) {
            isEditingMode = savedInstanceState.getBoolean(getString(R.string.StateEditingMode))
            if (isEditingMode) {
                startEditFields()

                // Restore edittext information
                binding.editTextTitle.setText(savedInstanceState.getString(
                    getString(R.string.StateETTitle)))
                binding.editTextOriginalTitle.setText(savedInstanceState.getString(
                    getString(R.string.StateETOriginalTitle)))
                binding.editTextDirector.setText(savedInstanceState.getString(
                    getString(R.string.StateETDirector)))
                binding.editTextProducer.setText(savedInstanceState.getString(
                    getString(R.string.StateETProducer)))
                binding.editTextReleaseDate.setText(savedInstanceState.getString(
                    getString(R.string.StateETReleaseDate)))
                binding.editTextScore.setText(savedInstanceState.getString(
                    getString(R.string.StateETScore)))
                binding.editTextDescription.setText(savedInstanceState.getString(
                    getString(R.string.StateETDescription)))
            }
        }

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

                // Only the edittext information is set when edit mode is false to avoid
                // loosing information when rotating the screen
                if (!isEditingMode) {
                    binding.editTextTitle.setText(it.title)
                    binding.editTextOriginalTitle.setText(it.original_title)
                    binding.editTextDirector.setText(it.director)
                    binding.editTextProducer.setText(it.producer)
                    binding.editTextReleaseDate.setText("${it.release_date}")
                    binding.editTextScore.setText("${it.rt_score}")
                    binding.editTextDescription.setText(it.description)
                }

                binding.imageViewFilm.loadByUrl(it.image)

                // Use databinding with textview
                binding.film = it
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save editing mode state
        outState.putBoolean(getString(R.string.StateEditingMode), isEditingMode)
        // Save edittext information modified
        outState.putString(getString(R.string.StateETTitle),
            binding.editTextTitle.text.toString())
        outState.putString(getString(R.string.StateETOriginalTitle),
            binding.editTextOriginalTitle.text.toString())
        outState.putString(getString(R.string.StateETDirector),
            binding.editTextDirector.text.toString())
        outState.putString(getString(R.string.StateETProducer),
            binding.editTextProducer.text.toString())
        outState.putString(getString(R.string.StateETReleaseDate),
            binding.editTextReleaseDate.text.toString())
        outState.putString(getString(R.string.StateETScore),
            binding.editTextScore.text.toString())
        outState.putString(getString(R.string.StateETDescription),
            binding.editTextDescription.text.toString())
    }

    /*
        Menu options
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_activity, menu)

        if (menu != null) {
            this.menu = menu
            checkItemMenuVisiblity(menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_edit_information -> {
                isEditingMode = true
                checkItemMenuVisiblity(menu)
                startEditFields()
                true
            }
            R.id.menu_delete_film -> {
                showConfirmDeleteFilmAlertDialog()
                true
            }
            R.id.menu_save_changes -> {
                if (binding.editTextScore.text.toString().toInt() <= 100) {

                    isEditingMode = false
                    // Update fields
                    binding.textViewTitle.text = binding.editTextTitle.text.toString()
                    binding.textViewOriginalTitle.text =
                        binding.editTextOriginalTitle.text.toString()
                    binding.textViewDirector.text = binding.editTextDirector.text.toString()
                    binding.textViewProducer.text = binding.editTextProducer.text.toString()
                    binding.textViewReleaseDate.text = binding.editTextReleaseDate.text.toString()
                    binding.textViewScore.text = binding.editTextScore.text.toString()
                    binding.textViewFilmDescription.text =
                        binding.editTextDescription.text.toString()

                    // Update database
                    filmsViewModel.updateFilmInformation(
                        filmID,
                        binding.textViewTitle.text.toString(),
                        binding.textViewOriginalTitle.text.toString(),
                        binding.textViewFilmDescription.text.toString(),
                        binding.textViewDirector.text.toString(),
                        binding.textViewProducer.text.toString(),
                        binding.textViewReleaseDate.text.toString().toInt(),
                        binding.textViewScore.text.toString().toInt()
                    )

                    checkItemMenuVisiblity(menu)
                    finishEditFields()
                } else {
                    Toast.makeText(this, getString(R.string.invalidScoreValueMessage),
                        Toast.LENGTH_LONG).show()
                }
                true
            }
            R.id.menu_discard_changes -> {
                isEditingMode = false
                // Set all textview information into edittext ("Reset" information)
                binding.editTextTitle.setText(binding.textViewTitle.text)
                binding.editTextOriginalTitle.setText(binding.textViewOriginalTitle.text)
                binding.editTextDirector.setText(binding.textViewDirector.text)
                binding.editTextProducer.setText(binding.textViewProducer.text)
                binding.editTextReleaseDate.setText(binding.textViewReleaseDate.text)
                binding.editTextScore.setText(binding.textViewScore.text)
                binding.editTextDescription.setText(binding.textViewFilmDescription.text)

                checkItemMenuVisiblity(menu)
                finishEditFields()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun checkItemMenuVisiblity(menu: Menu) {
        if (isEditingMode) {
            // Set visibility to item menu
            // Edit menu
            menu[0].isVisible = false
            // Delete menu
            menu[1].isVisible = false
            // Confirm menu
            menu[2].isVisible = true
            menu[3].isVisible = true
        } else {
            // Set visitiblity to item menu
            // Edit menu
            menu[0].isVisible = true
            // Delete menu
            menu[1].isVisible = true
            // Confirm menu
            menu[2].isVisible = false
            menu[3].isVisible = false
        }
    }

    /*
        Manage visibility from fields
     */
    fun startEditFields() {
        // Show edit warning information
        binding.textViewEditInformationMessage.visibility = View.VISIBLE

        // Hide textview and show edittext
        binding.textViewTitle.visibility = View.INVISIBLE
        binding.editTextTitle.visibility = View.VISIBLE

        binding.textViewOriginalTitle.visibility = View.INVISIBLE
        binding.editTextOriginalTitle.visibility = View.VISIBLE

        binding.textViewDirector.visibility = View.INVISIBLE
        binding.editTextDirector.visibility = View.VISIBLE

        binding.textViewProducer.visibility = View.INVISIBLE
        binding.editTextProducer.visibility = View.VISIBLE

        binding.textViewReleaseDate.visibility = View.INVISIBLE
        binding.editTextReleaseDate.visibility = View.VISIBLE

        binding.textViewScore.visibility = View.INVISIBLE
        binding.textViewPercentScoreMax.visibility = View.INVISIBLE
        binding.editTextScore.visibility = View.VISIBLE

        binding.textViewFilmDescription.visibility = View.INVISIBLE
        binding.editTextDescription.visibility = View.VISIBLE
    }

    fun finishEditFields() {
        // Hide edit warning information
        binding.textViewEditInformationMessage.visibility = View.GONE

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
        binding.textViewPercentScoreMax.visibility = View.VISIBLE
        binding.editTextScore.visibility = View.GONE

        binding.textViewFilmDescription.visibility = View.VISIBLE
        binding.editTextDescription.visibility = View.GONE
    }

    private fun showConfirmDeleteFilmAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.alertDialogEditTitle)
        builder.setMessage(R.string.alertDialogDeleteFilmMessage)

        builder.setPositiveButton(R.string.alertDialogEditPossitiveButton) { dialog, which ->
            filmsViewModel.deleteFilmById(filmID)
            goToActivity<MainActivity>()
            finish()
        }

        builder.setNegativeButton(R.string.alertDialogEditNegativeButton) { dialog, which ->
        }

        builder.show()
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