package com.shivam.learningapp.game2.beginner.g2level5exercise

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
import com.shivam.learningapp.databinding.ActivityG2BeginnersLv5ExerciseBinding

class G2BeginnersLv5Exercise : AppCompatActivity(), View.OnClickListener {

    var isCompleted1 = false
    var isCompleted2 = false
    var isCompleted3 = false

    private lateinit var click: MediaPlayer
    private lateinit var game: MediaPlayer
    private lateinit var binding: ActivityG2BeginnersLv5ExerciseBinding

    private lateinit var myAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityG2BeginnersLv5ExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        click = MediaPlayer.create(this, R.raw.bubble)
        game = MediaPlayer.create(this, R.raw.bg)
        game.isLooping = true

        val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val B1Lv4E1 = sharedPref.getBoolean("B1Lv4E1", false)
        val B1Lv4E2 = sharedPref.getBoolean("B1Lv4E2", false)
        val B1Lv4E3 = sharedPref.getBoolean("B1Lv4E3", false)

        isCompleted1 = B1Lv4E1
        isCompleted2 = B1Lv4E2
        isCompleted3 = B1Lv4E3

        val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.left_enter_views)
        val hyperspaceJumpAnimation2 = AnimationUtils.loadAnimation(this, R.anim.right_enter_views)

        binding.task2.startAnimation(hyperspaceJumpAnimation)
        binding.task3.startAnimation(hyperspaceJumpAnimation)
        binding.task1.startAnimation(hyperspaceJumpAnimation2)
        binding.button9.startAnimation(hyperspaceJumpAnimation2)

        myAnim = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        myAnim.duration = 1000

        val star1 = binding.imageView2
        val star2 = binding.imageView3
        val star3 = binding.imageView4

        val avatar1 = binding.imageView5
        val avatar2 = binding.imageView6
        val avatar3 = binding.imageView7

        val treasure = binding.button9

        if (isCompleted1) {
            binding.task2.setBackgroundResource(R.drawable.t2)
            star1.visibility = View.VISIBLE
            avatar1.visibility = View.INVISIBLE
            avatar2.visibility = View.VISIBLE
            avatar3.visibility = View.INVISIBLE
        }
        if (isCompleted2) {
            binding.task3.setBackgroundResource(R.drawable.t3)
            star2.visibility = View.VISIBLE
            avatar1.visibility = View.INVISIBLE
            avatar2.visibility = View.INVISIBLE
            avatar3.visibility = View.VISIBLE
        }
        if (isCompleted3) {
            treasure.setBackgroundResource(R.drawable.openchest)
            star3.visibility = View.VISIBLE
            avatar1.visibility = View.INVISIBLE
            avatar2.visibility = View.INVISIBLE
            avatar3.visibility = View.INVISIBLE
        }

        if (isCompleted1 && isCompleted2 && isCompleted3) {
            editor.putBoolean("B1Lv5", true)
            editor.apply()
        }

        binding.task1.setOnClickListener(this)
        binding.task2.setOnClickListener(this)
        binding.task3.setOnClickListener(this)
        binding.back.setOnClickListener(this)
        treasure.setOnClickListener(this)
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
        if (v.id == R.id.task1) {
            click.start()
            binding.task1.startAnimation(myAnim)
            myAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(a: Animation) {}
                override fun onAnimationRepeat(a: Animation) {}
                override fun onAnimationEnd(a: Animation) {
                    val i = Intent(this@G2BeginnersLv5Exercise, Game2Activity::class.java)
                    i.putExtra("level", "B1Lv5E1")
                    startActivity(i)
                }
            })
        } else if (v.id == R.id.task2) {
            if (isCompleted1) {
                click.start()
                binding.task2.startAnimation(myAnim)
                myAnim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(a: Animation) {}
                    override fun onAnimationRepeat(a: Animation) {}
                    override fun onAnimationEnd(a: Animation) {
                        val i = Intent(this@G2BeginnersLv5Exercise, Game2Activity::class.java)
                        i.putExtra("level", "B1Lv5E2")
                        startActivity(i)
                    }
                })
            }
        } else if (v.id == R.id.task3) {
            if (isCompleted2) {
                click.start()
                binding.task3.startAnimation(myAnim)
                myAnim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(a: Animation) {}
                    override fun onAnimationRepeat(a: Animation) {}
                    override fun onAnimationEnd(a: Animation) {
                        val i = Intent(this@G2BeginnersLv5Exercise, Game2Activity::class.java)
                        i.putExtra("level", "B1Lv5E3")
                        startActivity(i)
                    }
                })
            }
        } else if (v.id == R.id.button9) {
            if (isCompleted1 && isCompleted2 && isCompleted3) {
                binding.button9.startAnimation(myAnim)
                myAnim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(a: Animation) {}
                    override fun onAnimationRepeat(a: Animation) {}
                    override fun onAnimationEnd(a: Animation) {
                        val i = Intent(this@G2BeginnersLv5Exercise, G2LevelsActivityBeginners::class.java)
                        startActivity(i)
                    }
                })
            }
        } else if (v.id == R.id.back) {
            binding.back.startAnimation(myAnim)
            click.start()
            val i = Intent(this@G2BeginnersLv5Exercise, G2LevelsActivityBeginners::class.java)
            startActivity(i)
            overridePendingTransition(R.anim.left_enter, R.anim.right_out)
        }
    }
}
