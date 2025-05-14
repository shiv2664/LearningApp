package com.shivam.learningapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shivam.learningapp.databinding.ActivityResultMainBinding
import com.shivam.learningapp.game3alphabet.AlphabetsPlayground
import com.shivam.learningapp.game4jumbledalphabets.Game4JumbledAlphabets

class ResultActivityMain : AppCompatActivity() {

    private lateinit var binding: ActivityResultMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Game=intent.getStringExtra("Game")

        binding.PlayNext.setOnClickListener {
            if (Game=="Game4JumbledAlphabets"){
                val intent= Intent(this,Game4JumbledAlphabets::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.right_enter,R.anim.left_out)
            }else if ( Game=="AlphabetsPlayground"){
                val intent= Intent(this,AlphabetsPlayground::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.right_enter,R.anim.left_out)
            }
        }
    }
}