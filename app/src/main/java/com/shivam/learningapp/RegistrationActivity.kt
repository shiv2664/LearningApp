package com.shivam.learningapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.shivam.learningapp.databinding.ActivityRegistrationBinding
//import co.gofynd.gravityview.GravityView
import com.shivam.learningapp.fragments.LoginFragment
import com.shivam.learningapp.fragments.SignUpFragment

class RegistrationActivity : AppCompatActivity() ,IMainActivity {

    private var fragmentmanager: FragmentManager?=null
//    private lateinit var gravityView : GravityView
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            launcherbg.setBackgroundResource(R.drawable.twf_background)

//            gravityView = GravityView.getInstance(this)
//            gravityView.setImage(bg, R.drawable.launcherbg).center()
        }

//        if (gravityView.deviceSupported()) {
//            gravityView.setImage(bg, R.drawable.launcherbg).center()
//        } else {
//            launcherbg.setBackgroundResource(R.drawable.twf_background)
//        }



        fragmentmanager= supportFragmentManager
        fragmentmanager!!
            .beginTransaction()
            .setCustomAnimations(R.anim.bottom_enter,R.anim.bottom_out)
            .add(R.id.fragmentcontainer,LoginFragment())
            .commit()

    }

    override fun onRegisterClick() {
        fragmentmanager= supportFragmentManager
        fragmentmanager!!
            .beginTransaction()
            .setCustomAnimations(R.anim.right_enter,R.anim.left_out)
            .addToBackStack("Login Fragment")
            .replace(R.id.fragmentcontainer, SignUpFragment(),"SignUp Fragment")
            .commit();

    }

    override fun onBackPressed() {
        val signUpFragment = fragmentmanager?.findFragmentByTag("SignUp Fragment")
        if (signUpFragment != null) {
            replaceLoginFragment()
        }
        else super.onBackPressed()
    }

    private fun replaceLoginFragment() {
        fragmentmanager!!.popBackStack()
        fragmentmanager!!
            .beginTransaction()
            .setCustomAnimations(R.anim.left_enter,R.anim.right_out)
            .replace(R.id.fragmentcontainer, LoginFragment())
            .commit()
//        textView1.text = "Welcome Back"
//        textView2.text="We missed you! Login to get started"
//        textAnim()
    }

//    private fun textAnim(){
//        val view=findViewById<View>(R.id.textView1)
//        ObjectAnimator.ofFloat(view, View.ALPHA,0f,1f).apply { duration=2000 }.start()
//        val view2=findViewById<View>(R.id.textView2)
//        ObjectAnimator.ofFloat(view2, View.ALPHA,0f,1f).apply { duration=2000 }.start()
//
//    }

    override fun onResume() {
        super.onResume()
//        gravityView.registerListener()
    }

    override fun onStop() {
        super.onStop()
//        gravityView.unRegisterListener()
    }

}