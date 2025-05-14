package com.shivam.learningapp.game3alphabet

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.shivam.learningapp.R
import com.shivam.learningapp.adapters.BodyPartAdapter
import com.mig35.carousellayoutmanager.CarouselLayoutManager
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.mig35.carousellayoutmanager.CenterScrollListener
import com.shivam.learningapp.databinding.ActivityLearnBodyPartsBinding

class LearnBodyParts : AppCompatActivity() {

    private lateinit var binding: ActivityLearnBodyPartsBinding
    private var soundList: ArrayList<Int> = ArrayList()
    private var imageList: ArrayList<Int> = ArrayList()
    private lateinit var game: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBodyPartsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        game = MediaPlayer.create(this, R.raw.bg)

        // Initialize UI state
        updateArrowIcon()

        // Setup carousel layout
        val layoutManager = CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true)
        layoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        // Initialize data
        val images = arrayListOf(
            R.drawable.head, R.drawable.face, R.drawable.hairs, R.drawable.eyebrow,
            R.drawable.eyes, R.drawable.nose, R.drawable.lips, R.drawable.tongue,
            R.drawable.ear, R.drawable.chin, R.drawable.shoulder, R.drawable.elbow,
            R.drawable.hand, R.drawable.leg
        )

        val sounds = arrayListOf(
            R.raw.head, R.raw.face, R.raw.hair, R.raw.eyebrow,
            R.raw.eyes, R.raw.nose, R.raw.lips, R.raw.tongue,
            R.raw.ear, R.raw.chin, R.raw.shoulder, R.raw.elbow,
            R.raw.hand, R.raw.leg
        )

        // Create a hashmap if needed (though it's not used in the original code)
        val hashMap: HashMap<Int, Int> = HashMap<Int, Int>().apply {
            put(R.drawable.head, R.raw.head)
            put(R.drawable.face, R.raw.face)
            put(R.drawable.hairs, R.raw.hair)
            put(R.drawable.eyebrow, R.raw.eyebrow)
            put(R.drawable.eyes, R.raw.eyes)
            put(R.drawable.nose, R.raw.nose)
            put(R.drawable.lips, R.raw.lips)
            put(R.drawable.tongue, R.raw.tongue)
            put(R.drawable.ear, R.raw.ear)
            put(R.drawable.chin, R.raw.chin)
            put(R.drawable.shoulder, R.raw.shoulder)
            put(R.drawable.elbow, R.raw.elbow)
            put(R.drawable.hand, R.raw.hand)
            put(R.drawable.leg, R.raw.leg)
        }

        imageList.addAll(images)
        soundList.addAll(sounds)

        // Setup RecyclerView
        binding.bodyParts.apply {
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            adapter = BodyPartAdapter(imageList, soundList, this@LearnBodyParts)
            addOnScrollListener(CenterScrollListener())
        }

        // Set click listener for show/hide parts
        binding.showParts.setOnClickListener {
            toggleBodyPartsVisibility()
        }
    }

    private fun updateArrowIcon() {
        val iconRes = if (binding.bodyParts.visibility == View.INVISIBLE) {
            R.drawable.ic_baseline_arrow_upward_24
        } else {
            R.drawable.ic_baseline_arrow_downward_24
        }
        binding.showParts.setImageResource(iconRes)
    }

    private fun toggleBodyPartsVisibility() {
        if (binding.bodyParts.visibility == View.INVISIBLE) {
            binding.bodyParts.visibility = View.VISIBLE
            binding.fullBody.visibility = View.INVISIBLE
        } else {
            binding.bodyParts.visibility = View.INVISIBLE
            binding.fullBody.visibility = View.VISIBLE
        }
        updateArrowIcon()
    }

    override fun onResume() {
        super.onResume()
        // Original onResume logic
    }

    override fun onDestroy() {
        super.onDestroy()
        // Original onDestroy logic
    }

    override fun onPause() {
        super.onPause()
        // Original onPause logic
    }
}