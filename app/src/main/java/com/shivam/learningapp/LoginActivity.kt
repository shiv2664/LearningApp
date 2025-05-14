package com.shivam.learningapp

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.shivam.learningapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textAnim()

        binding.loginGoogle.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun textAnim(){
        val view=findViewById<View>(R.id.textView1)
        ObjectAnimator.ofFloat(view, View.ALPHA,0f,1f).apply { duration=2000 }.start()
        val view2=findViewById<View>(R.id.textView2)
        ObjectAnimator.ofFloat(view2, View.ALPHA,0f,1f).apply { duration=2000 }.start()

    }
}