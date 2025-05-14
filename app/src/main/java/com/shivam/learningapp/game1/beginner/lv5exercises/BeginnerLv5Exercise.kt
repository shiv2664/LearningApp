package com.shivam.learningapp.game1.beginner.lv5exercises

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
import com.shivam.learningapp.databinding.ActivityBeginnerLv5ExerciseBinding


class BeginnerLv5Exercise : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityBeginnerLv5ExerciseBinding
    private lateinit var click: MediaPlayer
    private lateinit var game: MediaPlayer
    private lateinit var myAnim: Animation

    var isCompleted1 = true
    var isCompleted2 = true
    var isCompleted3 = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerLv5ExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        isCompleted1 = sharedPref.getBoolean("G1B1Lv4E1", false)
        isCompleted2 = sharedPref.getBoolean("G1B1Lv4E2", false)
        isCompleted3 = sharedPref.getBoolean("G1B1Lv4E3", false)

        click = MediaPlayer.create(this, R.raw.bubble)
        game = MediaPlayer.create(this, R.raw.bg)
        if (DashboardActivity.volumeMain) {
            game.isLooping = true
        }

        val animLeft = AnimationUtils.loadAnimation(this, R.anim.left_enter_views)
        val animRight = AnimationUtils.loadAnimation(this, R.anim.right_enter_views)

        binding.apply {
            task2.startAnimation(animLeft)
            task3.startAnimation(animLeft)
            task1.startAnimation(animRight)
            button9.startAnimation(animRight)
            back.startAnimation(animLeft)
        }

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        myAnim.interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.duration = 1000

        binding.apply {
            if (isCompleted1) {
                task2.setBackgroundResource(R.drawable.t2)
                imageView2.visibility = View.VISIBLE
                imageView5.visibility = View.INVISIBLE
                imageView6.visibility = View.VISIBLE
                imageView7.visibility = View.INVISIBLE
            }
            if (isCompleted2) {
                task3.setBackgroundResource(R.drawable.t3)
                imageView3.visibility = View.VISIBLE
                imageView5.visibility = View.INVISIBLE
                imageView6.visibility = View.INVISIBLE
                imageView7.visibility = View.VISIBLE
            }
            if (isCompleted3) {
                button9.setBackgroundResource(R.drawable.openchest)
                imageView4.visibility = View.VISIBLE
                imageView5.visibility = View.INVISIBLE
                imageView6.visibility = View.INVISIBLE
                imageView7.visibility = View.INVISIBLE
            }
        }

        if (isCompleted1 && isCompleted2 && isCompleted3) {
            editor.putBoolean("G1B1Lv5", true)
            editor.apply()
        }

        binding.apply {
            task1.setOnClickListener(this@BeginnerLv5Exercise)
            task2.setOnClickListener(this@BeginnerLv5Exercise)
            task3.setOnClickListener(this@BeginnerLv5Exercise)
            button9.setOnClickListener(this@BeginnerLv5Exercise)
            back.setOnClickListener(this@BeginnerLv5Exercise)
        }
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
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.task1 -> {
                click.start()
                binding.task1.startAnimation(myAnim)
                myAnim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(a: Animation) {}
                    override fun onAnimationRepeat(a: Animation) {}
                    override fun onAnimationEnd(a: Animation) {
                        startActivity(Intent(this@BeginnerLv5Exercise, Game1Activity::class.java).apply {
                            putExtra("level", "G1B1Lv5E1")
                        })
                    }
                })
            }
            R.id.task2 -> {
                if (isCompleted1) {
                    click.start()
                    binding.task2.startAnimation(myAnim)
                    myAnim.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(a: Animation) {}
                        override fun onAnimationRepeat(a: Animation) {}
                        override fun onAnimationEnd(a: Animation) {
                            startActivity(Intent(this@BeginnerLv5Exercise, Game1Activity::class.java).apply {
                                putExtra("level", "G1B1Lv5E2")
                            })
                        }
                    })
                }
            }
            R.id.task3 -> {
                if (isCompleted2) {
                    click.start()
                    binding.task3.startAnimation(myAnim)
                    myAnim.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(a: Animation) {}
                        override fun onAnimationRepeat(a: Animation) {}
                        override fun onAnimationEnd(a: Animation) {
                            startActivity(Intent(this@BeginnerLv5Exercise, Game1Activity::class.java).apply {
                                putExtra("level", "G1B1Lv5E3")
                            })
                        }
                    })
                }
            }
            R.id.button9 -> {
                if (isCompleted1 && isCompleted2 && isCompleted3) {
                    binding.button9.startAnimation(myAnim)
                    myAnim.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(a: Animation) {}
                        override fun onAnimationRepeat(a: Animation) {}
                        override fun onAnimationEnd(a: Animation) {
                            startActivity(Intent(this@BeginnerLv5Exercise, LevelsActivityBeginners::class.java))
                        }
                    })
                }
            }
            R.id.back -> {
                binding.back.startAnimation(myAnim)
                click.start()
                startActivity(Intent(this@BeginnerLv5Exercise, LevelsActivityBeginners::class.java))
                overridePendingTransition(R.anim.left_enter, R.anim.right_out)
            }
        }
    }
}
