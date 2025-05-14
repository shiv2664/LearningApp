package com.shivam.learningapp.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.shivam.learningapp.R
import com.shivam.learningapp.adapters.G2ResultAdapter
import com.shivam.learningapp.databinding.FragmentG2ResultBinding
import com.shivam.learningapp.game2.beginner.G2LevelsActivityBeginners
import com.shivam.learningapp.utils.MyBounceInterpolator

class G2ResultFragment : Fragment() {

    private lateinit var binding:FragmentG2ResultBinding
    var correctAnswers :ArrayList<String>?=null
    var questions :ArrayList<String>?=null
    private lateinit var click: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentG2ResultBinding.inflate(inflater,container,false)

        var myAnim: Animation? = null
        myAnim = AnimationUtils.loadAnimation(activity, R.anim.bounce)
        click = MediaPlayer.create(activity, R.raw.bubble)

        val bundle = this.arguments
        correctAnswers= bundle?.getStringArrayList("correctAnswers") as ArrayList<String>
        questions= bundle.getStringArrayList("questions") as ArrayList<String>

        binding.back.setOnClickListener {

            binding.back.startAnimation(myAnim)
            click.start()
            // Use bounce interpolator with amplitude 0.2 and frequency 20
            val interpolator = MyBounceInterpolator(0.2, 20.0)
            myAnim.interpolator = interpolator
            myAnim.duration = 1000
            val intent = Intent(activity, G2LevelsActivityBeginners::class.java)
            startActivity(intent)
            activity?.finish()
            activity?.overridePendingTransition(R.anim.left_enter,R.anim.right_out)

        }


        val adapter1= activity?.let { G2ResultAdapter(correctAnswers!!, questions!!, it) }

        val linearLayoutManager= LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)

        binding.g2ResultRecyclerview.layoutManager=linearLayoutManager
        binding.g2ResultRecyclerview.adapter=adapter1





        return binding.root
    }
}