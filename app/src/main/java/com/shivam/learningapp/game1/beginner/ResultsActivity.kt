package com.shivam.learningapp.game1.beginner

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.shivam.learningapp.R
import com.shivam.learningapp.databinding.ActivityResultsBinding
import com.shivam.learningapp.fragments.ResultFragment
import com.shivam.learningapp.game1.beginner.lv1exercises.BeginnerLv1Exercise
import com.shivam.learningapp.game1.beginner.lv2exercises.BeginnerLv2Exercise
import com.shivam.learningapp.game1.beginner.lv3exercises.BeginnerLv3Exercise
import com.shivam.learningapp.game1.beginner.lv4exercises.BeginnerLv4Exercise
import com.shivam.learningapp.game1.beginner.lv5exercises.BeginnerLv5Exercise

class ResultsActivity : AppCompatActivity() {

    private var fragmentmanager: FragmentManager?=null
    private lateinit var correctAnswers :ArrayList<String>
    private lateinit var game: MediaPlayer

    private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_results)

        binding.apply {


            val score = intent.getIntExtra("marks", 0)
            val level = intent.getStringExtra("level")
            val gameLevel = intent.getStringExtra("gameLevel")
            val progressLevel = intent.getStringExtra("progressLevel")
            correctAnswers = intent.getStringArrayListExtra("correctAnswers") as ArrayList<String>

            val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()

            if (progressLevel == "G1B1Lv1E1" && score >= 60) {
                editor.putBoolean("G1B1Lv1E1", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv1E2" && score >= 60) {
                editor.putBoolean("G1B1Lv1E2", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv1E3" && score >= 60) {
                editor.putBoolean("G1B1Lv1E3", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv2E1" && score >= 60) {
                editor.putBoolean("G1B1Lv2E1", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv2E2" && score >= 60) {
                editor.putBoolean("G1B1Lv2E2", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv2E3" && score >= 60) {
                editor.putBoolean("G1B1Lv2E3", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv3E1" && score >= 60) {
                editor.putBoolean("G1B1Lv3E1", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv3E2" && score >= 60) {
                editor.putBoolean("G1B1Lv3E2", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv3E3" && score >= 60) {
                editor.putBoolean("G1B1Lv3E3", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv4E1" && score >= 60) {
                editor.putBoolean("G1B1Lv4E1", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv4E2" && score >= 60) {
                editor.putBoolean("G1B1Lv1E2", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv4E3" && score >= 60) {
                editor.putBoolean("G1B1Lv4E3", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv5E1" && score >= 60) {
                editor.putBoolean("G1B1Lv5E1", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv5E2" && score >= 60) {
                editor.putBoolean("G1B1Lv5E2", true)
                editor.apply()
            } else if (progressLevel == "G1B1Lv5E3" && score >= 60) {
                editor.putBoolean("G1B1Lv5E3", true)
                editor.apply()
            }

            if (score < 60) {

                game = MediaPlayer.create(this@ResultsActivity, R.raw.tryagain)
                game.start()
                message.text = "Your quiz score is still too low :("
                PlayNext.text = "Try Again"
            } else {
                game = MediaPlayer.create(this@ResultsActivity, R.raw.marioyipee)
                game.start()
                lottieanimecongo.visibility = View.VISIBLE
                message.text = "Congratulations! Your quiz score.."
                PlayNext.text = "Play Next"
            }

            textlevel.text = "Beginner Level"

//        when (level) {
//            "1" -> {
//                textlevel.text = "Beginner Level"
//            }
//            "2" -> {
//                textlevel.text = "Advanced Level"
//            }
//            else -> {
//                textlevel.text = "Professional Level"
//            }
//        }

            val result = score.toString()

            grade.text = "$result/100"
            PlayNext.setOnClickListener {

                if (gameLevel == "G1B1Lv1") {
                    val intent = Intent(this@ResultsActivity, BeginnerLv1Exercise::class.java)
                    startActivity(intent)
                    finish()
                    overridePendingTransition(R.anim.left_enter, R.anim.right_out)
                } else if (gameLevel == "G1B1Lv2") {
                    val intent = Intent(this@ResultsActivity, BeginnerLv2Exercise::class.java)
                    startActivity(intent)
                    finish()
                    overridePendingTransition(R.anim.left_enter, R.anim.right_out)
                } else if (gameLevel == "G1B1Lv3") {
                    val intent = Intent(this@ResultsActivity, BeginnerLv3Exercise::class.java)
                    startActivity(intent)
                    finish()
                    overridePendingTransition(R.anim.left_enter, R.anim.right_out)
                } else if (gameLevel == "G1B1Lv4") {
                    val intent = Intent(this@ResultsActivity, BeginnerLv4Exercise::class.java)
                    startActivity(intent)
                    finish()
                    overridePendingTransition(R.anim.left_enter, R.anim.right_out)
                } else if (gameLevel == "G1B1Lv5") {
                    val intent = Intent(this@ResultsActivity, BeginnerLv5Exercise::class.java)
                    startActivity(intent)
                    finish()
                    overridePendingTransition(R.anim.left_enter, R.anim.right_out)
                }

            }

            GoBack.setOnClickListener {
                val intent = Intent(this@ResultsActivity, LevelsActivityBeginners::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.left_enter, R.anim.right_out)
            }

            Results.setOnClickListener {

                val fragment = ResultFragment()

                val bundle = Bundle()
                bundle.putStringArrayList("correctAnswers", correctAnswers)
                fragment.arguments = bundle
                fragmentmanager = supportFragmentManager

                fragmentmanager!!
                    .beginTransaction()
                    .setCustomAnimations(R.anim.bottom_enter, R.anim.bottom_out)
                    .replace(R.id.fragmentcontainer, fragment, "ResultFragment")
                    .commit();
            }


        }

    }
}