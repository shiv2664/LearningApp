package com.shivam.learningapp.game1.beginner.lv3exercises

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.shivam.learningapp.DashboardActivity
import com.shivam.learningapp.R
import com.shivam.learningapp.game1.Game1Activity
import com.shivam.learningapp.game1.beginner.LevelsActivityBeginners
import com.shivam.learningapp.utils.MyBounceInterpolator
import com.google.android.material.button.MaterialButton
import com.shivam.learningapp.databinding.ActivityBeginnerLv3ExerciseBinding


class BeginnerLv3Exercise : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityBeginnerLv3ExerciseBinding

    var isCompleted1 = true
    var isCompleted2 = true
    var isCompleted3 = true

    private lateinit var click: MediaPlayer
    private lateinit var game: MediaPlayer

    private lateinit var myAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerLv3ExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        isCompleted1 = sharedPref.getBoolean("G1B1Lv3E1", false)
        isCompleted2 = sharedPref.getBoolean("G1B1Lv3E2", false)
        isCompleted3 = sharedPref.getBoolean("G1B1Lv3E3", false)

        click = MediaPlayer.create(this, R.raw.bubble)
        game = MediaPlayer.create(this, R.raw.bg)

        if (DashboardActivity.volumeMain) {
            game.isLooping = true
        }

        val leftAnim = AnimationUtils.loadAnimation(this, R.anim.left_enter_views)
        val rightAnim = AnimationUtils.loadAnimation(this, R.anim.right_enter_views)

        binding.task2.startAnimation(leftAnim)
        binding.task3.startAnimation(leftAnim)
        binding.task1.startAnimation(rightAnim)
        binding.button9.startAnimation(rightAnim)
        binding.back.startAnimation(leftAnim)

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        myAnim.interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.duration = 1000

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

        if (isCompleted1 && isCompleted2 && isCompleted3) {
            editor.putBoolean("G1B1Lv3", true)
            editor.apply()
        }

        binding.task1.setOnClickListener(this)
        binding.task2.setOnClickListener(this)
        binding.task3.setOnClickListener(this)
        binding.button9.setOnClickListener(this)
        binding.back.setOnClickListener(this)
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
                        val i = Intent(this@BeginnerLv3Exercise, Game1Activity::class.java)
                        i.putExtra("level", "G1B1Lv3E1")
                        startActivity(i)
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
                            val i = Intent(this@BeginnerLv3Exercise, Game1Activity::class.java)
                            i.putExtra("level", "G1B1Lv3E2")
                            startActivity(i)
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
                            val i = Intent(this@BeginnerLv3Exercise, Game1Activity::class.java)
                            i.putExtra("level", "G1B1Lv3E3")
                            startActivity(i)
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
                            val i = Intent(this@BeginnerLv3Exercise, LevelsActivityBeginners::class.java)
                            startActivity(i)
                        }
                    })
                }
            }

            R.id.back -> {
                binding.back.startAnimation(myAnim)
                click.start()
                val i = Intent(this@BeginnerLv3Exercise, LevelsActivityBeginners::class.java)
                Log.e("mylog", "in level1")
                startActivity(i)
                overridePendingTransition(R.anim.left_enter, R.anim.right_out)
            }
        }
    }
}
