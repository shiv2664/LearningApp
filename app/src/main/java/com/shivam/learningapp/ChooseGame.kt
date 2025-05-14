package com.shivam.learningapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.shivam.learningapp.databinding.ActivityChooseGameBinding
import com.shivam.learningapp.utils.MyBounceInterpolator

class ChooseGame : AppCompatActivity() {

    private lateinit var click: MediaPlayer
    private lateinit var game: MediaPlayer
    private lateinit var myAnim: Animation

    private lateinit var binding: ActivityChooseGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChooseGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        click = MediaPlayer.create(this, R.raw.bubble)
        game = MediaPlayer.create(this, R.raw.bg)
        if (DashboardActivity.volumeMain){
            game.isLooping = true
        }
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator
        myAnim.duration = 1000

binding.apply {

    buttonPlayMatchMe.setOnClickListener {
        val intent = Intent(this@ChooseGame, ExercisesMatchMe::class.java)
        startActivity(intent)
    }

    buttonPlaySpellMe.setOnClickListener {

        val intent = Intent(this@ChooseGame, ExerciseJumbledAlphabets::class.java)
        startActivity(intent)

    }
    back.setOnClickListener {
        back.startAnimation(myAnim)
        click.start()
        val i = Intent(this@ChooseGame, DashboardActivity::class.java)
        Log.e("mylog", "in level1")
        startActivity(i)
        overridePendingTransition(R.anim.left_enter,R.anim.right_out)
    }
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
//        adapter1.clearItems()
//        adapter2.clearItems()
//        recycler_view_1.adapter=null
    }

    override fun onPause() {
        super.onPause()
        game.pause()
//        adapter1.clearItems()
//        adapter2.clearItems()
    }
}