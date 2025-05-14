package com.shivam.learningapp.game3alphabet

import android.content.Intent
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shivam.learningapp.LevelsLearn
import com.shivam.learningapp.R
import com.shivam.learningapp.adapters.LandMarksAdapter
import com.mig35.carousellayoutmanager.CarouselLayoutManager
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.mig35.carousellayoutmanager.CenterScrollListener
import com.shivam.learningapp.databinding.ActivityLearnMonumentsBinding
import es.dmoral.toasty.Toasty

class LearnMonuments : AppCompatActivity() {
    private lateinit var binding: ActivityLearnMonumentsBinding
    private val nameList = ArrayList<String>()
    private val placeList = ArrayList<String>()
    private val imageList = ArrayList<Int>()
    private lateinit var game: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnMonumentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkNetworkConnection()
        initializeMediaPlayer()
        setupBackButton()
        setupRecyclerView()
    }

    private fun checkNetworkConnection() {
        if (!isNetworkAvailable()) {
            binding.learnMonument.visibility = View.INVISIBLE
            Toasty.info(this, "No network", Toasty.LENGTH_SHORT, true).show()
        }
    }

    private fun initializeMediaPlayer() {
        game = MediaPlayer.create(this, R.raw.gamesound)
        // Original commented code:
        // if (DashboardActivity.volumeMain) { game.isLooping = true }
    }

    private fun setupBackButton() {
        binding.buttonback.setOnClickListener {
            val intent = Intent(this, LevelsLearn::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.right_enter, R.anim.left_out)
        }
    }

    private fun setupRecyclerView() {
        // Initialize data lists
        val names = listOf(
            "The Statue of Liberty", "The Eiffel Tower", "St. Basil's Cathedral",
            "Blue Domed Church", "The Great Sphinx", "The Pyramids of Giza",
            // ... (rest of the names)
            "Sydney Opera House"
        )

        val places = listOf(
            "New York, USA", "Paris, France", "Moscow, Russia",
            "Santorini, Greece", "Giza, Egypt", "Giza in Egypt",
            // ... (rest of the places)
            "Australia"
        )

        val images = listOf(
            R.drawable.liberty, R.drawable.eiffel, R.drawable.st_basils_cathedral,
            R.drawable.blue_domed, R.drawable.sphnix, R.drawable.pyramids,
            // ... (rest of the images)
            R.drawable.sydneyoperahouse
        )

        nameList.addAll(names)
        placeList.addAll(places)
        imageList.addAll(images)

        // Original commented code for adding duplicates:
        // imageList.add(6, imageList[0]) etc.

        // Setup RecyclerView with Carousel layout
        val layoutManager = CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true).apply {
            setPostLayoutListener(CarouselZoomPostLayoutListener())
        }

        binding.learnMonument.apply {
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            adapter = LandMarksAdapter(imageList, placeList, nameList, this@LearnMonuments)
            addOnScrollListener(CenterScrollListener())
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onResume() {
        super.onResume()
        // Original commented code:
        // if (DashboardActivity.volumeMain) { game.isLooping = true; game.start() }
    }

    override fun onDestroy() {
        super.onDestroy()
        game.release()
        // Original commented code:
        // adapter1.clearItems(); recycler_view_1.adapter = null
    }

    override fun onPause() {
        super.onPause()
        // Original commented code:
        // game.pause(); adapter1.clearItems()
    }
}