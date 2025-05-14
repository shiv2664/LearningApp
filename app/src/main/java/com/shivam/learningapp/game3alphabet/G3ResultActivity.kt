package com.shivam.learningapp.game3alphabet

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.shivam.learningapp.ChooseGame
import com.shivam.learningapp.DashboardActivity
import com.shivam.learningapp.R
import com.shivam.learningapp.databinding.ActivityG3ResultBinding

class G3ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityG3ResultBinding
    private lateinit var game: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_g3_result)

        val score = intent.getIntExtra("marks", 0)
        Log.d("MyTag", "score is :$score")

        // Binding data directly to the UI
        binding.grade.text = "$score/100"

        if (score < 60) {
            game = MediaPlayer.create(this, R.raw.tryagain)
            game.start()
            binding.message.text = "Your quiz score is still too low :("
            binding.PlayNext.text = "Try Again"
        } else {
            game = MediaPlayer.create(this, R.raw.marioyipee)
            game.start()
            binding.lottieanimecongo.visibility = View.VISIBLE
            binding.message.text = "Congratulations! Your quiz score.."
            binding.PlayNext.text = "Play Again"
        }

        binding.textlevel.text = "Beginner Level"

        binding.PlayNext.setOnClickListener {
            val intent = Intent(this, ChooseGame::class.java)
            startActivity(intent)
            finish()
        }

        binding.GoBack.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.left_enter, R.anim.right_out)
        }
    }
}
