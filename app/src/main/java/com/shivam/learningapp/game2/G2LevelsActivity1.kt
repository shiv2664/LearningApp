package com.shivam.learningapp.game2

import android.R.attr.button
import android.R.id.button2
import android.R.id.button3
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.shivam.learningapp.DashboardActivity
import com.shivam.learningapp.R
import com.shivam.learningapp.databinding.ActivityBeginnerLv5ExerciseBinding
import com.shivam.learningapp.game2.beginner.G2LevelsActivityBeginners
import com.shivam.learningapp.utils.MyBounceInterpolator
import es.dmoral.toasty.Toasty

class G2LevelsActivity1 : AppCompatActivity() {

    private lateinit var click: MediaPlayer
    var level: String = ""

    private lateinit var binding: ActivityBeginnerLv5ExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginnerLv5ExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var myAnim: Animation? = null
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        click = MediaPlayer.create(this, R.raw.bubble)
        binding.apply {

            //        val spaceshipImage: ImageView = findViewById<View>(R.id.spaceshipImage) as ImageView
            val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this@G2LevelsActivity1, R.anim.left_enter_views)
            val hyperspaceJumpAnimation2 = AnimationUtils.loadAnimation(this@G2LevelsActivity1, R.anim.right_enter_views)
            /*button.startAnimation(hyperspaceJumpAnimation)
            button2.startAnimation(hyperspaceJumpAnimation)
            button3.startAnimation(hyperspaceJumpAnimation)
            selectButton.startAnimation(hyperspaceJumpAnimation2)*/


            /*button.setOnClickListener {

                click.start()

                level = "1"
                button.setBackgroundResource(R.drawable.circlegreen)
                button.setTextColor(resources.getColor(R.color.colorPrimaryDark))

                button2.setTextColor(resources.getColor(R.color.white))
                button3.setTextColor(resources.getColor(R.color.white))
                button2.setBackgroundResource(R.drawable.circlepurple)
                button3.setBackgroundResource(R.drawable.circlepurple)
            }*/

            /*button2.setOnClickListener {

                click.start()

                level = "2"
                button2.setBackgroundResource(R.drawable.circlegreen)
                button2.setTextColor(resources.getColor(R.color.colorPrimaryDark))

                button.setTextColor(resources.getColor(R.color.white))
                button3.setTextColor(resources.getColor(R.color.white))
                button.setBackgroundResource(R.drawable.circlepurple)
                button3.setBackgroundResource(R.drawable.circlepurple)
            }*/

            /*button3.setOnClickListener {

                click.start()

                level = "3"
                button3.setBackgroundResource(R.drawable.circlegreen)
                button3.setTextColor(resources.getColor(R.color.colorPrimaryDark))

                button.setTextColor(resources.getColor(R.color.white))
                button2.setTextColor(resources.getColor(R.color.white))
                button2.setBackgroundResource(R.drawable.circlepurple)
                button.setBackgroundResource(R.drawable.circlepurple)
            }*/

            /*selectButton.setOnClickListener {
                click.start()

                if (level == "3" || level == "2") {
                    Toasty.info(
                        this,
                        "Please clear the earlier levels before accessing the future levels",
                        Toasty.LENGTH_SHORT,
                        true
                    ).show()
                } else if (level == "1") {
                    val intent = Intent(this, G2LevelsActivityBeginners::class.java)
                    startActivity(intent)
//                val intent = Intent(this, G2ResultActivity::class.java)
//                startActivity(intent)
                } else {

                    Toasty.info(
                        this, "Please select a level", Toasty.LENGTH_SHORT, true
                    ).show()

                }


            }*/

            back.setOnClickListener {

                back.startAnimation(myAnim)
                click.start()
                // Use bounce interpolator with amplitude 0.2 and frequency 20
                val interpolator = MyBounceInterpolator(0.2, 20.0)
                myAnim.interpolator = interpolator
                myAnim.duration = 1000

                val intent = Intent(this@G2LevelsActivity1, DashboardActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.left_enter, R.anim.right_out)

            }

        }



    }
}