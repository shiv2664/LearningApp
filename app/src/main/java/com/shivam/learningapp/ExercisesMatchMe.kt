package com.shivam.learningapp

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.shivam.learningapp.game3alphabet.AlphabetsPlayground
import com.shivam.learningapp.utils.MyBounceInterpolator
import com.google.android.material.button.MaterialButton
import com.shivam.learningapp.databinding.ActivityExercisesMatchMeBinding

class ExercisesMatchMe : AppCompatActivity(), View.OnClickListener {

    var isCompleted1 = false
    var isCompleted2 = false
    var isCompleted3 = false

    private lateinit var click: MediaPlayer
    private lateinit var game: MediaPlayer
    private lateinit var binding: ActivityExercisesMatchMeBinding
    private lateinit var myAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesMatchMeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val G3B1Lv1E1 = sharedPref.getBoolean("G3B1Lv1E1", false)
        val G3B1Lv1E2 = sharedPref.getBoolean("G3B1Lv1E2", false)
        val G3B1Lv1E3 = sharedPref.getBoolean("G3B1Lv1E3", false)

        isCompleted1 = G3B1Lv1E1
        isCompleted2 = G3B1Lv1E2
        isCompleted3 = G3B1Lv1E3

        click = MediaPlayer.create(this, R.raw.bubble)
        game = MediaPlayer.create(this, R.raw.bg)
        game.isLooping = true

        val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.left_enter_views)
        val hyperspaceJumpAnimation2 = AnimationUtils.loadAnimation(this, R.anim.right_enter_views)

        binding.task2.startAnimation(hyperspaceJumpAnimation)
        binding.task3.startAnimation(hyperspaceJumpAnimation)
        binding.task1.startAnimation(hyperspaceJumpAnimation2)
        binding.button9.startAnimation(hyperspaceJumpAnimation2)
        binding.back.startAnimation(hyperspaceJumpAnimation)

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
            editor.putBoolean("G3B1Lv1", true)
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
                        val i = Intent(this@ExercisesMatchMe, AlphabetsPlayground::class.java)
                        i.putExtra("level", "G3B1Lv1E1")
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
                            val i = Intent(this@ExercisesMatchMe, AlphabetsPlayground::class.java)
                            i.putExtra("level", "G3B1Lv1E2")
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
                            val i = Intent(this@ExercisesMatchMe, AlphabetsPlayground::class.java)
                            i.putExtra("level", "G3B1Lv1E3")
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
                            binding.animlevelcomplete.visibility = View.VISIBLE
                        }
                    })
                }
            }
            R.id.back -> {
                binding.back.startAnimation(myAnim)
                click.start()
                val i = Intent(this@ExercisesMatchMe, ChooseGame::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.left_enter, R.anim.right_out)
            }
        }
    }
}