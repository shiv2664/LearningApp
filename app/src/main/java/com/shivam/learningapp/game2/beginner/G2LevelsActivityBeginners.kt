package com.shivam.learningapp.game2.beginner

import android.content.Context
import android.content.Intent
import android.graphics.LightingColorFilter
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.shivam.learningapp.R
import com.shivam.learningapp.databinding.ActivityG2LevelsBeginnersBinding
import com.shivam.learningapp.game2.G2LevelsActivity1
import com.shivam.learningapp.game2.beginner.g2level1exercise.G2BeginnersLv1Exercise
import com.shivam.learningapp.game2.beginner.g2level2exercise.G2BeginnersLv2Exercise
import com.shivam.learningapp.game2.beginner.g2level3exercise.G2BeginnersLv3Exercise
import com.shivam.learningapp.game2.beginner.g2level4exercise.G2BeginnersLv4Exercise
import com.shivam.learningapp.game2.beginner.g2level5exercise.G2BeginnersLv5Exercise
import com.shivam.learningapp.utils.MyBounceInterpolator
import java.util.ArrayList

class G2LevelsActivityBeginners : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityG2LevelsBeginnersBinding
    private lateinit var game: MediaPlayer
    private lateinit var click: MediaPlayer
    private lateinit var myAnim: Animation

    var isLevel1: Boolean? = null
    var isLevel2: Boolean? = null
    var isLevel3: Boolean? = null
    var isLevel4: Boolean? = null
    var isLevel5: Boolean? = null

    var tasks1 = ArrayList<String>()
    var tasks2 = ArrayList<String>()
    var tasks3 = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_g2_levels_beginners)

        val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
        isLevel1 = sharedPref.getBoolean("B1Lv1", false)
        isLevel2 = sharedPref.getBoolean("B1Lv2", false)
        isLevel3 = sharedPref.getBoolean("B1Lv3", false)
        isLevel4 = sharedPref.getBoolean("B1Lv4", false)
        isLevel5 = sharedPref.getBoolean("B1Lv5", false)

        val animLeft = AnimationUtils.loadAnimation(this, R.anim.left_enter_views)
        val animRight = AnimationUtils.loadAnimation(this, R.anim.right_enter_views)

        binding.level1.startAnimation(animLeft)
        binding.level2.startAnimation(animLeft)
        binding.level4.startAnimation(animLeft)
        binding.level3.startAnimation(animRight)
        binding.level5.startAnimation(animRight)

        game = MediaPlayer.create(this, R.raw.bg).apply { isLooping = true }
        click = MediaPlayer.create(this, R.raw.bubble)

        binding.back.setOnClickListener(this)

        if (isLevel1 == true) {
            binding.level1.setBackgroundResource(R.drawable.open3star)
            binding.level2.setBackgroundResource(R.drawable.open0star)
        }
        if (isLevel2 == true) {
            binding.level2.setBackgroundResource(R.drawable.open3star)
            binding.level3.setBackgroundResource(R.drawable.open0star)
        }
        if (isLevel3 == true) {
            binding.level3.setBackgroundResource(R.drawable.open3star)
            binding.level4.setBackgroundResource(R.drawable.open0star)
        }
        if (isLevel4 == true) {
            binding.level4.setBackgroundResource(R.drawable.open3star)
            binding.level5.setBackgroundResource(R.drawable.open0star)
        }
        if (isLevel5 == true) {
            binding.level5.setBackgroundResource(R.drawable.open3star)
        }

        // Remove color filter initially
        resetLevelButtonColors()

        // Click listeners
        binding.level1.setOnClickListener(this)
        binding.level2.setOnClickListener(this)
        binding.level3.setOnClickListener(this)
        binding.level4.setOnClickListener(this)
        binding.level5.setOnClickListener(this)

        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce).apply {
            interpolator = MyBounceInterpolator(0.2, 20.0)
            duration = 1000
        }
    }

    override fun onStart() {
        super.onStart()
        resetLevelButtonColors()
    }

    override fun onResume() {
        super.onResume()
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
            R.id.level1 -> {
                binding.level1.startAnimation(myAnim)
                startLevel(G2BeginnersLv1Exercise::class.java, "b1")
                binding.level1.background.colorFilter = LightingColorFilter(-0x100, 0x0000FF00)
            }
            R.id.level2 -> {
                if (isLevel1 == true) {
                    binding.level2.startAnimation(myAnim)
                    startLevel(G2BeginnersLv2Exercise::class.java, "b2")
                    binding.level2.background.colorFilter = LightingColorFilter(-0x1, -0xff00f10)
                }
            }
            R.id.level3 -> {
                if (isLevel2 == true) {
                    binding.level3.startAnimation(myAnim)
                    startLevel(G2BeginnersLv3Exercise::class.java, "b3")
                    binding.level3.background.colorFilter = LightingColorFilter(-0x1, -0xff00f10)
                }
            }
            R.id.level4 -> {
                if (isLevel3 == true) {
                    binding.level4.startAnimation(myAnim)
                    startLevel(G2BeginnersLv4Exercise::class.java, "b4")
                    binding.level4.background.colorFilter = LightingColorFilter(-0x1, -0xff00f10)
                }
            }
            R.id.level5 -> {
                if (isLevel4 == true) {
                    binding.level5.startAnimation(myAnim)
                    startLevel(G2BeginnersLv5Exercise::class.java, "b5")
                    binding.level5.background.colorFilter = LightingColorFilter(-0x1, -0xff00f10)
                }
            }
            R.id.back -> {
                binding.back.startAnimation(myAnim)
                click.start()
                val intent = Intent(this, G2LevelsActivity1::class.java)
                intent.putExtra("level", "4")
                startActivity(intent)
                overridePendingTransition(R.anim.left_enter, R.anim.right_out)
            }
        }
    }

    private fun startLevel(cls: Class<*>, level: String) {
        val intent = Intent(this, cls)
        intent.putExtra("level", level)
        startActivity(intent)
    }

    private fun resetLevelButtonColors() {
        with(binding) {
            val filter = LightingColorFilter(-0x1, 0x00000000)
            level1.background.colorFilter = filter
            level2.background.colorFilter = filter
            level3.background.colorFilter = filter
            level4.background.colorFilter = filter
            level5.background.colorFilter = filter
        }
    }
}

