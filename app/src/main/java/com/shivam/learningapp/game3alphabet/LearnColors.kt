package com.shivam.learningapp.game3alphabet

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shivam.learningapp.LevelsLearn
import com.shivam.learningapp.R
import com.shivam.learningapp.databinding.ActivityLearnColorsBinding

class LearnColors : AppCompatActivity() {

    private lateinit var binding: ActivityLearnColorsBinding
    private lateinit var sound1: MediaPlayer
    private lateinit var sound2: MediaPlayer
    private lateinit var sound3: MediaPlayer
    private lateinit var sound4: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnColorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize MediaPlayer instances
        sound1 = MediaPlayer.create(this, R.raw.green)
        sound2 = MediaPlayer.create(this, R.raw.red)
        sound3 = MediaPlayer.create(this, R.raw.blue)
        sound4 = MediaPlayer.create(this, R.raw.yellow)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Back button click listener
        binding.buttonback.setOnClickListener {
            val intent = Intent(this, LevelsLearn::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.right_enter, R.anim.left_out)
        }

        // Color card click listeners
        binding.card1Green.setOnClickListener {
            sound1.start()
        }

        binding.card2Red.setOnClickListener {
            sound2.start()
        }

        binding.card3Blue.setOnClickListener {
            sound3.start()
        }

        binding.card4Yellow.setOnClickListener {
            sound4.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release MediaPlayer resources
        sound1.release()
        sound2.release()
        sound3.release()
        sound4.release()
    }
}