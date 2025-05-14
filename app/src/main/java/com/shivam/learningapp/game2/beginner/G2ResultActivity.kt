package com.shivam.learningapp.game2.beginner

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.shivam.learningapp.R
import com.shivam.learningapp.databinding.ActivityG2ResultBinding
import com.shivam.learningapp.fragments.G2ResultFragment
import com.shivam.learningapp.game2.beginner.g2level1exercise.G2BeginnersLv1Exercise
import com.shivam.learningapp.game2.beginner.g2level2exercise.G2BeginnersLv2Exercise
import com.shivam.learningapp.game2.beginner.g2level3exercise.G2BeginnersLv3Exercise
import com.shivam.learningapp.game2.beginner.g2level4exercise.G2BeginnersLv4Exercise
import com.shivam.learningapp.game2.beginner.g2level5exercise.G2BeginnersLv5Exercise

class G2ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityG2ResultBinding
    private lateinit var questions: ArrayList<String>
    private lateinit var correctAnswers: ArrayList<String>
    private var fragmentManager: FragmentManager? = null
    private lateinit var game: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityG2ResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
        val progressLevel = intent.getStringExtra("progressLevel")
        val editor = sharedPref.edit()

        questions = intent.getStringArrayListExtra("questions") as ArrayList<String>
        correctAnswers = intent.getStringArrayListExtra("correctAnswers") as ArrayList<String>
        val score = intent.getIntExtra("marks", 0)
        val level = intent.getStringExtra("level")
        val gameLevel = intent.getStringExtra("gameLevel")

        // Updating progress if score >= 60
        if (score >= 60) {
            progressLevel?.let { editor.putBoolean(it, true) }
            editor.apply()
        }

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
            binding.PlayNext.text = "Play Next"
        }

        binding.textlevel.text = "Beginner Level"
//        when (level) {
//            "1" -> binding.textlevel.text = "Beginner Level"
//            "2" -> binding.textlevel.text = "Advanced Level"
//            else -> binding.textlevel.text = "Professional Level"
//        }

        val result = score.toString()
        binding.grade.text = "$result/100"

        binding.PlayNext.setOnClickListener {
            val nextIntent = when (gameLevel) {
                "B1Lv1" -> Intent(this, G2BeginnersLv1Exercise::class.java)
                "B1Lv2" -> Intent(this, G2BeginnersLv2Exercise::class.java)
                "B1Lv3" -> Intent(this, G2BeginnersLv3Exercise::class.java)
                "B1Lv4" -> Intent(this, G2BeginnersLv4Exercise::class.java)
                "B1Lv5" -> Intent(this, G2BeginnersLv5Exercise::class.java)
                else -> null
            }
            nextIntent?.let {
                startActivity(it)
                finish()
                overridePendingTransition(R.anim.left_enter, R.anim.right_out)
            }
        }

        binding.GoBack.setOnClickListener {
            val intent = Intent(this, G2LevelsActivityBeginners::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.left_enter, R.anim.right_out)
        }

        binding.Results.setOnClickListener {
            val fragment = G2ResultFragment()
            val bundle = Bundle().apply {
                putStringArrayList("questions", questions)
                putStringArrayList("correctAnswers", correctAnswers)
            }
            fragment.arguments = bundle
            fragmentManager = supportFragmentManager
            fragmentManager!!
                .beginTransaction()
                .setCustomAnimations(R.anim.bottom_enter, R.anim.bottom_out)
                .replace(R.id.fragmentcontainer, fragment, "ResultFragment")
                .commit()
        }
    }
}

