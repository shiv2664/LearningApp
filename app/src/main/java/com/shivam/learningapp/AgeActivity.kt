package com.shivam.learningapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shivam.learningapp.databinding.ActivityAgeBinding
import es.dmoral.toasty.Toasty

class AgeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAgeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sharedPref =getSharedPreferences("Age", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        binding.startButton.setOnClickListener {
            if(binding.age.text.toString()!="") {

                editor.putString("Age", binding.age.text.toString())
                editor.apply()
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toasty.normal(this,"Please enter your age ",Toasty.LENGTH_LONG).show()
            }
        }


    }
}