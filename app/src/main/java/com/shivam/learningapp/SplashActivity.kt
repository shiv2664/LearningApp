package com.shivam.learningapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val sharedPref = getSharedPreferences("Age", Context.MODE_PRIVATE)
        val age=sharedPref.getString("Age","0")
        val ageInt= age?.toInt()

        if (ageInt==0){
            val intent=Intent(this, AgeActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent=Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }




//        lottieanim.duration.compareTo(3000)
//        val sharedPref =getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
//        val sharedPref =getSharedPreferences( Context.MODE_PRIVATE)

//        val sharedPref2: SharedPreferences = getSharedPreferences("Status",Context.MODE_PRIVATE)
//        val userStatus = sharedPref2.getString("UserStatus", "LoggedOut")
//        if (userStatus=="LoggedOut"){
//            val intent=Intent(this,RegistrationActivity::class.java)
//            startActivity(intent)
//            finish()
//        }else if(userStatus=="LoggedIn"){
//            val intent=Intent(this, DashboardActivity::class.java)
//            startActivity(intent)
//            finish()
//        }


//        Handler(Looper.getMainLooper()).postDelayed({
//            run {
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }, 2000); // Millisecond 1000 = 1 sec

    }
}