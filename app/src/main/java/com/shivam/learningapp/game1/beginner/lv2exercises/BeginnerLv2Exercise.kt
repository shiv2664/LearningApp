package com.shivam.learningapp.game1.beginner.lv2exercises

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.shivam.learningapp.DashboardActivity
import com.shivam.learningapp.R
import com.shivam.learningapp.game1.Game1Activity
import com.shivam.learningapp.game1.beginner.LevelsActivityBeginners
import com.shivam.learningapp.utils.MyBounceInterpolator
import com.google.android.material.button.MaterialButton
import com.shivam.learningapp.databinding.ActivityBeginnerLv2ExerciseBinding

class BeginnerLv2Exercise : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityBeginnerLv2ExerciseBinding
    private lateinit var click: MediaPlayer
    private lateinit var game: MediaPlayer
    private lateinit var myAnim: Animation

    private var isCompleted1 = true
    private var isCompleted2 = true
    private var isCompleted3 = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerLv2ExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize shared preferences
        val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        isCompleted1 = sharedPref.getBoolean("G1B1Lv2E1", false)
        isCompleted2 = sharedPref.getBoolean("G1B1Lv2E2", false)
        isCompleted3 = sharedPref.getBoolean("G1B1Lv2E3", false)

        // Initialize media players
        click = MediaPlayer.create(this, R.raw.bubble)
        game = MediaPlayer.create(this, R.raw.bg)
        if (DashboardActivity.volumeMain) {
            game.isLooping = true
        }

        // Setup animations
        setupAnimations()
        setupLevelCompletionUI()
        setupClickListeners()

        if (isCompleted1 && isCompleted2 && isCompleted3) {
            editor.putBoolean("G1B1Lv2", true)
            editor.apply()
        }
    }

    private fun setupAnimations() {
        // Entry animations
        val leftEnterAnimation = AnimationUtils.loadAnimation(this, R.anim.left_enter_views)
        val rightEnterAnimation = AnimationUtils.loadAnimation(this, R.anim.right_enter_views)

        binding.task2.startAnimation(leftEnterAnimation)
        binding.task3.startAnimation(leftEnterAnimation)
        binding.task1.startAnimation(rightEnterAnimation)
        binding.button9.startAnimation(rightEnterAnimation)
        binding.back.startAnimation(leftEnterAnimation)

        // Bounce animation
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce).apply {
            interpolator = MyBounceInterpolator(0.2, 20.0)
            duration = 1000
        }
    }

    private fun setupLevelCompletionUI() {
        if (isCompleted1) {
            binding.task2.setBackgroundResource(R.drawable.t2)
            binding.imageView2.visibility = View.VISIBLE
            binding.imageView5.visibility = View.INVISIBLE
            binding.imageView6.visibility = View.VISIBLE
            binding.imageView7.visibility = View.INVISIBLE
        }
        if (isCompleted2) {
            binding.task3.setBackgroundResource(R.drawable.t3)
            binding.imageView3.visibility = View.VISIBLE
            binding.imageView5.visibility = View.INVISIBLE
            binding.imageView6.visibility = View.INVISIBLE
            binding.imageView7.visibility = View.VISIBLE
        }
        if (isCompleted3) {
            binding.button9.setBackgroundResource(R.drawable.openchest)
            binding.imageView4.visibility = View.VISIBLE
            binding.imageView5.visibility = View.INVISIBLE
            binding.imageView6.visibility = View.INVISIBLE
            binding.imageView7.visibility = View.INVISIBLE
        }
    }

    private fun setupClickListeners() {
        binding.task1.setOnClickListener(this)
        binding.task2.setOnClickListener(this)
        binding.task3.setOnClickListener(this)
        binding.back.setOnClickListener(this)
        binding.button9.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (DashboardActivity.volumeMain) {
            game.isLooping = true
            game.start()
        }
    }

    override fun onPause() {
        super.onPause()
        game.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up resources if needed
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.task1 -> handleTaskClick(binding.task1, "G1B1Lv2E1")
            R.id.task2 -> if (isCompleted1) handleTaskClick(binding.task2, "G1B1Lv2E2")
            R.id.task3 -> if (isCompleted2) handleTaskClick(binding.task3, "G1B1Lv2E3")
            R.id.button9 -> if (isCompleted1 && isCompleted2 && isCompleted3) handleTreasureClick()
            R.id.back -> handleBackClick()
        }
    }

    private fun handleTaskClick(button: View, level: String) {
        click.start()
        button.startAnimation(myAnim)
        myAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@BeginnerLv2Exercise, Game1Activity::class.java).apply {
                    putExtra("level", level)
                })
            }
        })
    }

    private fun handleTreasureClick() {
        binding.button9.startAnimation(myAnim)
        myAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@BeginnerLv2Exercise, LevelsActivityBeginners::class.java))
            }
        })
    }

    private fun handleBackClick() {
        binding.back.startAnimation(myAnim)
        click.start()
        startActivity(Intent(this@BeginnerLv2Exercise, LevelsActivityBeginners::class.java))
        overridePendingTransition(R.anim.left_enter, R.anim.right_out)
    }
}