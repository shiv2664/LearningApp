package com.shivam.learningapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.shivam.learningapp.databinding.ActivityAdvGamesBinding
import com.shivam.learningapp.game1.beginner.LevelsActivityBeginners
import com.shivam.learningapp.utils.MyBounceInterpolator
import es.dmoral.toasty.Toasty

class AdvGames : AppCompatActivity() {

    private lateinit var click: MediaPlayer
    private lateinit var binding: ActivityAdvGamesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdvGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            click = MediaPlayer.create(this@AdvGames, R.raw.bubble)
            var myAnim: Animation? = null
            myAnim = AnimationUtils.loadAnimation(this@AdvGames, R.anim.bounce)

            buttonPlayJumbledWords.setOnClickListener {
                val intent =Intent(this@AdvGames,LevelsActivityBeginners::class.java)
                startActivity(intent)
            }

            buttonPlaySynonyms.setOnClickListener {
                Toasty.normal(this@AdvGames,"Game is currently locked",Toasty.LENGTH_SHORT).show()
            }

            back.setOnClickListener {

                back.startAnimation(myAnim)
                click.start()
                // Use bounce interpolator with amplitude 0.2 and frequency 20
                val interpolator = MyBounceInterpolator(0.2, 20.0)
                myAnim.interpolator = interpolator
                myAnim.duration = 1000
                val intent = Intent(this@AdvGames, DashboardActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.left_enter,R.anim.right_out)

            }

        }


    }
}