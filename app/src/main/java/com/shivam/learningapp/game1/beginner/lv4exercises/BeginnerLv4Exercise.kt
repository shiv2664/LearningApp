package com.shivam.learningapp.game1.beginner.lv4exercises

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
import com.shivam.learningapp.databinding.ActivityBeginnerLv4ExerciseBinding

class BeginnerLv4Exercise : AppCompatActivity(), View.OnClickListener {

    private var isCompleted1 = true
    private var isCompleted2 = true
    private var isCompleted3 = true

    private lateinit var click: MediaPlayer
    private lateinit var game: MediaPlayer
    private lateinit var myAnim: Animation

    private lateinit var binding: ActivityBeginnerLv4ExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerLv4ExerciseBinding.inflate(layoutInflater)
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

        with(binding) {
            task2.startAnimation(animLeft)
            task3.startAnimation(animLeft)
            task1.startAnimation(animRight)
            button9.startAnimation(animRight)
            back.startAnimation(animLeft)
        }

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce).apply {
            interpolator = MyBounceInterpolator(0.2, 20.0)
            duration = 1000
        }

        with(binding) {
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
            editor.putBoolean("G1B1Lv4", true)
            editor.apply()
        }

        with(binding) {
            task1.setOnClickListener(this@BeginnerLv4Exercise)
            task2.setOnClickListener(this@BeginnerLv4Exercise)
            task3.setOnClickListener(this@BeginnerLv4Exercise)
            button9.setOnClickListener(this@BeginnerLv4Exercise)
            back.setOnClickListener(this@BeginnerLv4Exercise)
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
        with(binding) {
            when (v.id) {
                R.id.task1 -> {
                    click.start()
                    task1.startAnimation(myAnim)
                    myAnim.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation) {}
                        override fun onAnimationRepeat(animation: Animation) {}
                        override fun onAnimationEnd(animation: Animation) {
                            startActivity(Intent(this@BeginnerLv4Exercise, Game1Activity::class.java).apply {
                                putExtra("level", "G1B1Lv4E1")
                            })
                        }
                    })
                }

                R.id.task2 -> {
                    if (isCompleted1) {
                        click.start()
                        task2.startAnimation(myAnim)
                        myAnim.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation) {}
                            override fun onAnimationRepeat(animation: Animation) {}
                            override fun onAnimationEnd(animation: Animation) {
                                startActivity(Intent(this@BeginnerLv4Exercise, Game1Activity::class.java).apply {
                                    putExtra("level", "G1B1Lv4E2")
                                })
                            }
                        })
                    }
                }

                R.id.task3 -> {
                    if (isCompleted2) {
                        click.start()
                        task3.startAnimation(myAnim)
                        myAnim.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation) {}
                            override fun onAnimationRepeat(animation: Animation) {}
                            override fun onAnimationEnd(animation: Animation) {
                                startActivity(Intent(this@BeginnerLv4Exercise, Game1Activity::class.java).apply {
                                    putExtra("level", "G1B1Lv4E3")
                                })
                            }
                        })
                    }
                }

                R.id.button9 -> {
                    if (isCompleted1 && isCompleted2 && isCompleted3) {
                        button9.startAnimation(myAnim)
                        myAnim.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation) {}
                            override fun onAnimationRepeat(animation: Animation) {}
                            override fun onAnimationEnd(animation: Animation) {
                                startActivity(Intent(this@BeginnerLv4Exercise, LevelsActivityBeginners::class.java))
                            }
                        })
                    }
                }

                R.id.back -> {
                    back.startAnimation(myAnim)
                    click.start()
                    startActivity(Intent(this@BeginnerLv4Exercise, LevelsActivityBeginners::class.java))
                    overridePendingTransition(R.anim.left_enter, R.anim.right_out)
                }
            }
        }
    }
}
