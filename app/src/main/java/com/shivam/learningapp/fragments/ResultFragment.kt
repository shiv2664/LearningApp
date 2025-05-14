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
import com.shivam.learningapp.adapters.ResultAdapter
import com.shivam.learningapp.databinding.FragmentResultBinding
import com.shivam.learningapp.game1.beginner.LevelsActivityBeginners
import com.shivam.learningapp.utils.MyBounceInterpolator

class ResultFragment : Fragment() {

    private lateinit var binding:FragmentResultBinding
    var correctAnswers :ArrayList<String>?=null
    private lateinit var click: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentResultBinding.inflate(inflater,container,false)

        val bundle = this.arguments
        correctAnswers= bundle?.getStringArrayList("correctAnswers") as ArrayList<String>

        var myAnim: Animation? = null
        myAnim = AnimationUtils.loadAnimation(activity, R.anim.bounce)
        click = MediaPlayer.create(activity, R.raw.bubble)

        val adapter1= activity?.let { ResultAdapter(correctAnswers!!, it) }

        val linearLayoutManager= LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)

        binding.recyclerViewResult.layoutManager=linearLayoutManager
        binding.recyclerViewResult.adapter=adapter1

        binding.back.setOnClickListener {

            binding.back.startAnimation(myAnim)
            click.start()
            // Use bounce interpolator with amplitude 0.2 and frequency 20
            val interpolator = MyBounceInterpolator(0.2, 20.0)
            myAnim.interpolator = interpolator
            myAnim.duration = 1000
            val intent = Intent(activity, LevelsActivityBeginners::class.java)
            startActivity(intent)
            activity?.finish()
            activity?.overridePendingTransition(R.anim.left_enter,R.anim.right_out)

        }



        return binding.root
    }

}