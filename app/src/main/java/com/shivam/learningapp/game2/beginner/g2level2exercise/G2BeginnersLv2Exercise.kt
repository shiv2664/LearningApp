package com.shivam.learningapp.game2.beginner.g2level2exercise

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.shivam.learningapp.R
import com.shivam.learningapp.game2.Game2Activity
import com.shivam.learningapp.game2.beginner.G2LevelsActivityBeginners
import com.shivam.learningapp.utils.MyBounceInterpolator
import com.google.android.material.button.MaterialButton
import com.shivam.learningapp.databinding.ActivityG2BeginnersLv2ExerciseBinding

class G2BeginnersLv2Exercise : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityG2BeginnersLv2ExerciseBinding

    var isCompleted1 = false
    var isCompleted2 = false
    var isCompleted3 = false

    private lateinit var click: MediaPlayer
    private lateinit var game: MediaPlayer
    private lateinit var myAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityG2BeginnersLv2ExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        isCompleted1 = sharedPref.getBoolean("B1Lv2E1", false)
        isCompleted2 = sharedPref.getBoolean("B1Lv2E2", false)
        isCompleted3 = sharedPref.getBoolean("B1Lv2E3", false)

        click = MediaPlayer.create(this, R.raw.bubble)
        game = MediaPlayer.create(this, R.raw.bg)
        game.isLooping = true

        val animLeft = AnimationUtils.loadAnimation(this, R.anim.left_enter_views)
        val animRight = AnimationUtils.loadAnimation(this, R.anim.right_enter_views)

        binding.task2.startAnimation(animLeft)
        binding.task3.startAnimation(animLeft)
        binding.task1.startAnimation(animRight)
        binding.button9.startAnimation(animRight)
        binding.back.startAnimation(animLeft)

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
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
            editor.putBoolean("B1Lv2", true)
            editor.apply()
        }

        binding.task1.setOnClickListener(this)
        binding.task2.setOnClickListener(this)
        binding.task3.setOnClickListener(this)
        binding.back.setOnClickListener(this)
        binding.button9.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        game.isLooping = true
        game.start()
    }

    override fun onPause() {
        super.onPause()
        game.pause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        game.pause()
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
                        val i = Intent(this@G2BeginnersLv2Exercise, Game2Activity::class.java)
                        i.putExtra("level", "B1Lv2E1")
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
                            val i = Intent(this@G2BeginnersLv2Exercise, Game2Activity::class.java)
                            i.putExtra("level", "B1Lv2E2")
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
                            val i = Intent(this@G2BeginnersLv2Exercise, Game2Activity::class.java)
                            i.putExtra("level", "B1Lv2E3")
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
                            val i = Intent(this@G2BeginnersLv2Exercise, G2LevelsActivityBeginners::class.java)
                            startActivity(i)
                        }
                    })
                }
            }

            R.id.back -> {
                binding.back.startAnimation(myAnim)
                click.start()
                val i = Intent(this@G2BeginnersLv2Exercise, G2LevelsActivityBeginners::class.java)
                Log.e("mylog", "in level1")
                startActivity(i)
                overridePendingTransition(R.anim.left_enter, R.anim.right_out)
            }
        }
    }
}
