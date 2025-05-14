package com.shivam.learningapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.shivam.learningapp.databinding.ActivityLevelsLearnBinding
import com.shivam.learningapp.game3alphabet.LearnAlphabetActivity
import com.shivam.learningapp.game3alphabet.LearnBodyParts
import com.shivam.learningapp.game3alphabet.LearnColors
import com.shivam.learningapp.game3alphabet.LearnMonuments
import com.shivam.learningapp.utils.MyBounceInterpolator

class LevelsLearn : AppCompatActivity() {

    private lateinit var binding: ActivityLevelsLearnBinding
    private lateinit var click: MediaPlayer
    private lateinit var game: MediaPlayer
    private lateinit var myAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelsLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        click = MediaPlayer.create(this, R.raw.bubble)
        game = MediaPlayer.create(this, R.raw.bg)

        if (DashboardActivity.volumeMain) {
            game.isLooping = true
        }

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        myAnim.duration = 1000

        binding.buttonLearnAlphabet.setOnClickListener {
            click.start()
            val intent = Intent(this, LearnAlphabetActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLearnMonuments.setOnClickListener {
            click.start()
            val intent = Intent(this, LearnMonuments::class.java)
            startActivity(intent)
        }

        binding.buttonLearnColors.setOnClickListener {
            click.start()
            val intent = Intent(this, LearnColors::class.java)
            startActivity(intent)
        }

        binding.buttonLearnBodyparts.setOnClickListener {
            click.start()
            val intent = Intent(this, LearnBodyParts::class.java)
            startActivity(intent)
        }

        binding.back.setOnClickListener {
            binding.back.startAnimation(myAnim)
            click.start()
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.left_enter, R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
        if (DashboardActivity.volumeMain) {
            game.isLooping = true
            game.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up if needed
    }

    override fun onPause() {
        super.onPause()
        game.pause()
    }
}