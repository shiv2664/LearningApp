package com.shivam.learningapp.game1.beginner

import android.content.Context
import android.content.Intent
import android.graphics.LightingColorFilter
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.shivam.learningapp.AdvGames
import com.shivam.learningapp.DashboardActivity
import com.shivam.learningapp.R
import com.shivam.learningapp.databinding.ActivityLevelsBeginnersBinding
import com.shivam.learningapp.game1.LevelsActivity1
import com.shivam.learningapp.game1.beginner.lv1exercises.BeginnerLv1Exercise
import com.shivam.learningapp.game1.beginner.lv2exercises.BeginnerLv2Exercise
import com.shivam.learningapp.game1.beginner.lv3exercises.BeginnerLv3Exercise
import com.shivam.learningapp.game1.beginner.lv4exercises.BeginnerLv4Exercise
import com.shivam.learningapp.game1.beginner.lv5exercises.BeginnerLv5Exercise
import com.shivam.learningapp.utils.MyBounceInterpolator

import java.util.*

class LevelsActivityBeginners : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLevelsBeginnersBinding
    private lateinit var game: MediaPlayer
    private lateinit var click: MediaPlayer
    private lateinit var myAnim: Animation

    private var isLevel1: Boolean = false
    private var isLevel2: Boolean = false
    private var isLevel3: Boolean = false
    private var isLevel4: Boolean = false
    private var isLevel5: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelsBeginnersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
        isLevel1 = sharedPref.getBoolean("G1B1Lv1", false)
        isLevel2 = sharedPref.getBoolean("G1B1Lv2", false)
        isLevel3 = sharedPref.getBoolean("G1B1Lv3", false)
        isLevel4 = sharedPref.getBoolean("G1B1Lv4", false)
        isLevel5 = sharedPref.getBoolean("G1B1Lv5", false)

        // Initialize animations
        val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.left_enter_views)
        val hyperspaceJumpAnimation2 = AnimationUtils.loadAnimation(this, R.anim.right_enter_views)
        binding.level1.startAnimation(hyperspaceJumpAnimation)
        binding.level2.startAnimation(hyperspaceJumpAnimation)
        binding.level4.startAnimation(hyperspaceJumpAnimation)
        binding.level3.startAnimation(hyperspaceJumpAnimation2)
        binding.level5.startAnimation(hyperspaceJumpAnimation2)

        game = MediaPlayer.create(this, R.raw.bg)
        click = MediaPlayer.create(this, R.raw.bubble)

        if (DashboardActivity.volumeMain) {
            game.isLooping = true
        }

        // Initialize bounce animation
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce).apply {
            interpolator = MyBounceInterpolator(0.2, 20.0)
            duration = 1000
        }

        setupLevelButtons()
        setupClickListeners()
        resetColorFilters()
    }

    private fun setupLevelButtons() {
        if (isLevel1) {
            binding.level1.setBackgroundResource(R.drawable.open3star)
            binding.level2.setBackgroundResource(R.drawable.open0star)
        }
        if (isLevel2) {
            binding.level2.setBackgroundResource(R.drawable.open3star)
            binding.level3.setBackgroundResource(R.drawable.open0star)
        }
        if (isLevel3) {
            binding.level3.setBackgroundResource(R.drawable.open3star)
            binding.level4.setBackgroundResource(R.drawable.open0star)
        }
        if (isLevel4) {
            binding.level4.setBackgroundResource(R.drawable.open3star)
            binding.level5.setBackgroundResource(R.drawable.open0star)
        }
        if (isLevel5) {
            binding.level5.setBackgroundResource(R.drawable.open3star)
        }
    }

    private fun setupClickListeners() {
        binding.level1.setOnClickListener(this)
        binding.level2.setOnClickListener(this)
        binding.level3.setOnClickListener(this)
        binding.level4.setOnClickListener(this)
        binding.level5.setOnClickListener(this)
        binding.back2.setOnClickListener(this)
    }

    private fun resetColorFilters() {
        val colorFilter = LightingColorFilter(-0x1, 0x00000000)
        binding.level1.background.colorFilter = colorFilter
        binding.level2.background.colorFilter = colorFilter
        binding.level3.background.colorFilter = colorFilter
        binding.level4.background.colorFilter = colorFilter
        binding.level5.background.colorFilter = colorFilter
    }

    override fun onStart() {
        super.onStart()
        resetColorFilters()
    }

    override fun onResume() {
        super.onResume()
        if (DashboardActivity.volumeMain) {
            game.start()
            game.isLooping = true
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
            R.id.level1 -> {
                binding.level1.startAnimation(myAnim)
                binding.level1.background.colorFilter = LightingColorFilter(-0x100, 0x0000FF00)
                startActivity(Intent(this, BeginnerLv1Exercise::class.java))
            }
            R.id.level2 -> {
                if (isLevel1) {
                    binding.level2.startAnimation(myAnim)
                    binding.level2.background.colorFilter = LightingColorFilter(-0x1, -0xff00f10)
                    startActivity(Intent(this, BeginnerLv2Exercise::class.java))
                }
            }
            R.id.level3 -> {
                if (isLevel2) {
                    binding.level3.startAnimation(myAnim)
                    binding.level3.background.colorFilter = LightingColorFilter(-0x1, -0xff00f10)
                    startActivity(Intent(this, BeginnerLv3Exercise::class.java))
                }
            }
            R.id.level4 -> {
                if (isLevel3) {
                    binding.level4.startAnimation(myAnim)
                    binding.level4.background.colorFilter = LightingColorFilter(-0x1, -0xff00f10)
                    startActivity(Intent(this, BeginnerLv4Exercise::class.java))
                }
            }
            R.id.level5 -> {
                if (isLevel4) {
                    binding.level5.startAnimation(myAnim)
                    binding.level5.background.colorFilter = LightingColorFilter(-0x1, -0xff00f10)
                    startActivity(Intent(this, BeginnerLv5Exercise::class.java))
                }
            }
            R.id.back2 -> {
                binding.back2.startAnimation(myAnim)
                click.start()
                startActivity(Intent(this, AdvGames::class.java))
                overridePendingTransition(R.anim.left_enter, R.anim.right_out)
            }
        }
    }
}