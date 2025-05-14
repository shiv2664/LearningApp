package com.shivam.learningapp.game1

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.shivam.learningapp.DashboardActivity
import com.shivam.learningapp.R
import com.shivam.learningapp.databinding.ActivityLevels1Binding
import com.shivam.learningapp.game1.beginner.LevelsActivityBeginners
import com.shivam.learningapp.utils.MyBounceInterpolator
import es.dmoral.toasty.Toasty

class LevelsActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivityLevels1Binding
    private lateinit var click: MediaPlayer
    private var level: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevels1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        click = MediaPlayer.create(this, R.raw.bubble)

        // Initialize animations
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce).apply {
            interpolator = MyBounceInterpolator(0.2, 20.0)
            duration = 1000
        }

        val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.left_enter_views)
        val hyperspaceJumpAnimation2 = AnimationUtils.loadAnimation(this, R.anim.right_enter_views)

        // Apply animations
        binding.button.startAnimation(hyperspaceJumpAnimation)
        binding.button2.startAnimation(hyperspaceJumpAnimation)
        binding.button3.startAnimation(hyperspaceJumpAnimation)
        binding.selectButton.startAnimation(hyperspaceJumpAnimation2)

        // Button click listeners
        binding.button.setOnClickListener {
            click.start()
            level = "1"
            updateButtonAppearance(
                selectedButton = binding.button,
                unselectedButtons = listOf(binding.button2, binding.button3)
            )
        }

        binding.button2.setOnClickListener {
            click.start()
            level = "2"
            updateButtonAppearance(
                selectedButton = binding.button2,
                unselectedButtons = listOf(binding.button, binding.button3)
            )
        }

        binding.button3.setOnClickListener {
            click.start()
            level = "3"
            updateButtonAppearance(
                selectedButton = binding.button3,
                unselectedButtons = listOf(binding.button, binding.button2)
            )
        }

        binding.selectButton.setOnClickListener {
            click.start()
            when (level) {
                "3", "2" -> {
                    Toasty.info(this,
                        "Please clear the earlier levels before accessing the future levels",
                        Toasty.LENGTH_SHORT, true).show()
                }
                "1" -> {
                    startActivity(Intent(this, LevelsActivityBeginners::class.java))
                }
                else -> {
                    Toasty.info(this,
                        "Please select a level",
                        Toasty.LENGTH_SHORT, true).show()
                }
            }
        }

        binding.back.setOnClickListener {
            binding.back.startAnimation(myAnim)
            click.start()
            startActivity(Intent(this, DashboardActivity::class.java))
            overridePendingTransition(R.anim.left_enter, R.anim.right_out)
        }
    }

    private fun updateButtonAppearance(selectedButton: Button, unselectedButtons: List<Button>) {
        // Update selected button
        selectedButton.setBackgroundResource(R.drawable.circlegreen)
        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))

        // Update unselected buttons
        unselectedButtons.forEach { button ->
            button.setBackgroundResource(R.drawable.circlepurple)
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }
}