package com.shivam.learningapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shivam.learningapp.api.MyAuthWebService
import com.shivam.learningapp.databinding.ActivityDashboardBinding
import com.shivam.learningapp.game1.LevelsActivity1
import es.dmoral.toasty.Toasty

class DashboardActivity : AppCompatActivity() {

    companion object {
        var volumeMain: Boolean = true

        fun isConnectedToInternet(ctx: Context): Boolean {
            val connectivityManager = ctx
                .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = connectivityManager.activeNetworkInfo
            return ni != null && ni.isAvailable && ni.isConnected
        }
    }

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var game: MediaPlayer
    private lateinit var click: MediaPlayer
    private val animDuration: Long = 1000
    private var adLoadProgress = 0

    var x = 0
    var y: Int = 0
    var diaCount: Int = 1

    private val myTag = "MyTag"

    /*private var mRewardedAd: RewardedAd? = null
    private lateinit var rewardedAd: RewardedAd*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*var requestConfiguration = MobileAds.getRequestConfiguration()
            .toBuilder()
            .setTagForChildDirectedTreatment(RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
            .build()
        MobileAds.setRequestConfiguration(requestConfiguration)*/

        val sharedPref = getSharedPreferences("Age", Context.MODE_PRIVATE)
        val age = sharedPref.getString("Age", "3")
        val ageInt = age?.toInt()

        val sharedPref2 = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
        val G3B1Lv1 = sharedPref2.getBoolean("G3B1Lv1", false)
        val G4B1Lv1 = sharedPref2.getBoolean("G4B1Lv1", false)

        game = MediaPlayer.create(this, R.raw.bg)
        click = MediaPlayer.create(this, R.raw.bubble)

        if (volumeMain) {
            game.isLooping = true
        }

        Log.d(myTag, "G4B1Lv1 is : $G4B1Lv1 G3B1Lv1 is : $G3B1Lv1")

        if (ageInt != null) {
            if (ageInt <= 6 && G3B1Lv1 && G4B1Lv1) {
                binding.lottieanimeadv.visibility = View.INVISIBLE
            } else if (ageInt <= 6 || !G3B1Lv1 || !G4B1Lv1) {
                binding.lottieanimeadv.visibility = View.VISIBLE
            }
            if (ageInt > 6) {
                binding.lottieanimeadv.visibility = View.INVISIBLE
            }
        }

        binding.volume.setOnClickListener {
            if (game.isPlaying) {
                volumeMain = false
                game.pause()
                binding.volume.setBackgroundResource(R.drawable.voloff)
            } else if (!game.isPlaying) {
                volumeMain = true
                binding.volume.setBackgroundResource(R.drawable.volon)
                game.start()
            }
        }

        binding.buttonlearn.setOnClickListener {
            click.start()
            val intent = Intent(this, LevelsActivity1::class.java)
            startActivity(intent)
        }

        binding.Game2.setOnClickListener {
            click.start()
            val intent = Intent(this, ChooseGame::class.java)
            startActivity(intent)
        }

        binding.buttonHelp.setOnClickListener {
            val sharedPref2: SharedPreferences = getSharedPreferences("Status", Context.MODE_PRIVATE)
            val token = sharedPref2.getString("token", " ")
            Log.d(myTag, "token is : $token")

            val sharedPref = getSharedPreferences("Status", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("UserStatus", "LoggedOut")
            editor.remove("token")
            editor.apply()
            Toasty.info(this@DashboardActivity, "Logged Out", Toasty.LENGTH_SHORT, true).show()
            val intent = Intent(this@DashboardActivity, RegistrationActivity::class.java)
            startActivity(intent)
            finish()

            val mWebService2 = MyAuthWebService.retrofit?.create(MyAuthWebService::class.java)
            val logout = mWebService2?.logOutUser("Bearer $token")
            Log.d(myTag, "Clicked")
        }

        binding.buttonlearn.setOnClickListener {
            click.start()
            val intent = Intent(this, LevelsLearn::class.java)
            startActivity(intent)
        }

        binding.buttonComingSoon.setOnClickListener {
            // loadRewardedAd()
        }

        binding.buttonAdv.setOnClickListener {
            if (ageInt != null) {
                if (ageInt >= 6) {
                    val intent = Intent(this, AdvGames::class.java)
                    startActivity(intent)
                } else if (ageInt < 6 && G3B1Lv1 && G4B1Lv1) {
                    val intent = Intent(this, AdvGames::class.java)
                    startActivity(intent)
                } else {
                    Toasty.info(this, "Please Clear the earlier level first", Toasty.LENGTH_SHORT, true).show()
                }
            }
        }
    }

    private fun adsLoadedProgress() {
        ++adLoadProgress
        if (adLoadProgress == 3) {
            // Toast.makeText(this, "All ads are loaded", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if (volumeMain) {
            game.start()
            game.isLooping = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // adapter1.clearItems()
        // adapter2.clearItems()
        // recycler_view_1.adapter=null
    }

    override fun onPause() {
        super.onPause()
        game.pause()
        // adapter1.clearItems()
        // adapter2.clearItems()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}

//    private fun loadRewardedAd() {
//        val adRequest: AdRequest = AdRequest.Builder().build()
//
//        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",adRequest, object : RewardedAdLoadCallback() {
//                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                    // Handle the error.
//                    Log.d(myTag, loadAdError.message)
//                    mRewardedAd = null
//                    Log.d(myTag, "onAdFailedToLoad")
//                }
//
//                override fun onAdLoaded(rewardedAd: RewardedAd) {
//                    mRewardedAd = rewardedAd
//                    Log.d(myTag, "Ad is Loaded")
//                    showRewardedAd()
//                    mRewardedAd!!.fullScreenContentCallback = object :
//                        FullScreenContentCallback() {
//                        override fun onAdShowedFullScreenContent() {
//                            // Called when ad is shown.
//                            Log.d(myTag, "Ad was shown.")
//                            mRewardedAd = null
//
//                        }
//
//                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//                            // Called when ad fails to show.
//                            Log.d(myTag, "Ad failed to show.")
//                        }
//
//                        override fun onAdDismissedFullScreenContent() {
//                            // Called when ad is dismissed.
//                            // Don't forget to set the ad reference to null so you
//                            // don't show the ad a second time.
//                            Log.d(myTag, "Ad was dismissed.")
////                            loadRewardedAd()
//                        }
//                    }
//                }
//            })
//
//
//    }
//
//    private fun showRewardedAd() {
//        if (mRewardedAd != null) {
//            mRewardedAd!!.show(this) { rewardItem -> // Handle the reward.
//                Log.d(myTag, "The user earned the reward.")
//                val rewardAmount = rewardItem.amount
//                val rewardType = rewardItem.type
//                val reward = textView.text.toString().trim().toInt()
//                textView.text = (reward + rewardAmount).toString()
//            }
//        } else {
//            Log.d(myTag, "The rewarded ad wasn't ready yet.")
//        }
//    }
