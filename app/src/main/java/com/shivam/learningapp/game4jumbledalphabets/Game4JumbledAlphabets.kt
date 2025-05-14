package com.shivam.learningapp.game4jumbledalphabets

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.airbnb.lottie.LottieAnimationView
import com.shivam.learningapp.R
import com.shivam.learningapp.adapters.CustomAdapterWords
import com.shivam.learningapp.databinding.ActivityGame4JumbledAlphabetsBinding
import com.shivam.learningapp.game3alphabet.G3ResultActivity
import com.shivam.learningapp.interfaces.CustomListener
import es.dmoral.toasty.Toasty


class Game4JumbledAlphabets : AppCompatActivity(), CustomListener {

    private lateinit var binding: ActivityGame4JumbledAlphabetsBinding
    private val myTag = "MyTag"
    private lateinit var click: MediaPlayer
    private lateinit var game: MediaPlayer

    private val correctAnswers = ArrayList<String>()
    private lateinit var alphabetImageArray: List<Int>
    private val lastValues = ArrayList<String>()
    private lateinit var alphabetNameArray: List<String>
    private var adLoadProgress = 0

    private lateinit var adapter1: CustomAdapterWords
    private lateinit var adapter2: CustomAdapterWords

    private var random1: Int? = null
    private var random2: Int? = null
    private var random3: Int? = null
    private var random4: Int? = null
    private var random5: Int? = null

    private var correct = 0
    private var level: String? = null
//    private var mRewardedAd: RewardedAd? = null
//    private lateinit var rewardedAd: RewardedAd

    companion object {
        var checkSuccess: Boolean = false
        var stringList: MutableList<String>? = null
        lateinit var indianCultureList: MutableList<String>
        lateinit var sentenceOptions: MutableList<String>

        var notShuffleArrayList: ArrayList<String> = ArrayList()
        var shuffleArrayList: ArrayList<String> = ArrayList()
        var shuffleArrayList2: ArrayList<String> = ArrayList(shuffleArrayList.size)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGame4JumbledAlphabetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkNetworkConnection()
        initializeGame()
        setupData()
        setupRecyclerViews()
        setupClickListeners()
    }

    private fun checkNetworkConnection() {
        if (!isNetworkAvailable()) {
            binding.relativelayoutParent.visibility = View.INVISIBLE
            Toasty.info(this, "No network Please connect to internet", Toasty.LENGTH_SHORT, true).show()
        }
    }

    private fun initializeGame() {
        level = intent.getStringExtra("level")
        game = MediaPlayer.create(this, R.raw.bg)
        game.isLooping = true
    }

    private fun setupData() {
        alphabetImageArray = listOf(
            R.mipmap.a, R.mipmap.a1, R.mipmap.a2, R.mipmap.a3,
            R.mipmap.b, R.mipmap.b1, R.mipmap.b2, R.mipmap.b3,
            // ... (rest of the images)
            R.mipmap.z, R.mipmap.z1, R.mipmap.z2, R.mipmap.z3
        )

        alphabetNameArray = listOf(
            "Apple", "Airplane", "Ant", "Axe",
            "Banana", "Bat", "Box", "Bed",
            // ... (rest of the names)
            "Zebra", "Zip", "Zoo", "Zeppelin"
        )

        random1 = returnRandomWord()
        random2 = returnRandomWord()
        random3 = returnRandomWord()
        random4 = returnRandomWord()
        random5 = returnRandomWord()

        for (i in alphabetNameArray[random1!!]) {
            lastValues.add(i.toString())
            Log.d(myTag, "lastvalues is : $lastValues")
        }

        binding.nextHint.text = if (checkSuccess) "Next" else "Hint"
        binding.imagehint.setBackgroundResource(alphabetImageArray[random1!!])
    }

    private fun setupRecyclerViews() {
        notShuffleArrayList.addAll(lastValues)
        shuffleArrayList.addAll(lastValues)
        shuffleArrayList.shuffle()

        adapter1 = CustomAdapterWords(shuffleArrayList2, this, this, 0)
        adapter2 = CustomAdapterWords(shuffleArrayList, this, this, 1)

        binding.recyclerView1.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        binding.recyclerView2.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        binding.recyclerView1.adapter = adapter1
        binding.recyclerView2.adapter = adapter2

        binding.recyclerView1.setOnDragListener(adapter1.dragInstance)
        binding.recyclerView2.setOnDragListener(adapter2.dragInstance)
    }

    private fun setupClickListeners() {
        binding.volume.setOnClickListener {
            if (game.isPlaying) {
                game.pause()
                binding.volume.setBackgroundResource(R.drawable.voloff)
            } else {
                binding.volume.setBackgroundResource(R.drawable.volon)
                game.start()
            }
        }

        binding.next.setOnClickListener { handleNextClick() }
        binding.imagehint.setOnClickListener { toggleHintVisibility() }
        binding.imageCardView.setOnClickListener { toggleHintVisibility() }
        binding.nextHint.setOnClickListener { handleHintClick() }
    }

    private fun handleNextClick() {
        toggleHintVisibility()

        if (checkSuccess) {
            correct++
            val textViewMarks = 20 * correct
            Log.d("MyTag", "correct are : $correct")
            binding.textView7.text = textViewMarks.toString()
        }

        val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val number = 1 // This should be tracked as a class property

        lastValues.clear()
        shuffleArrayList.clear()
        notShuffleArrayList.clear()
        binding.nextHint.text = "Hint"
        checkSuccess = false
        binding.lottieanimecongo.visibility = View.INVISIBLE

        when (number) {
            2 -> updateForNextQuestion(random2!!)
            3 -> updateForNextQuestion(random3!!)
            4 -> updateForNextQuestion(random4!!)
            5 -> updateForNextQuestion(random5!!)
            else -> handleLevelCompletion(editor)
        }

        setupRecyclerViews()
    }

    private fun updateForNextQuestion(randomIndex: Int) {
        binding.imagehint.setBackgroundResource(alphabetImageArray[randomIndex])
        for (i in alphabetNameArray[randomIndex]) {
            lastValues.add(i.toString())
            Log.d(myTag, "lastvalues is : $lastValues")
        }
    }

    private fun handleLevelCompletion(editor: SharedPreferences.Editor) {
        if (level == "G4B1Lv1E1" && correct * 20 >= 60) {
            editor.putBoolean("G4B1Lv1E1", true)
        } else if (level == "G4B1Lv1E2" && correct * 20 >= 60) {
            editor.putBoolean("G4B1Lv1E2", true)
        } else if (level == "G4B1Lv1E3" && correct * 20 >= 60) {
            editor.putBoolean("G4B1Lv1E3", true)
        }
        editor.apply()

        val intent = Intent(this, G3ResultActivity::class.java).apply {
            putExtra("Game", "Game4JumbledAlphabets")
            putExtra("marks", correct * 20)
        }
        finish()
        startActivity(intent)
    }

    private fun toggleHintVisibility() {
        if (binding.imageCardView.visibility == View.VISIBLE || binding.imagehint.visibility == View.VISIBLE) {
            binding.imageCardView.visibility = View.INVISIBLE
            binding.imagehint.visibility = View.INVISIBLE
        }
    }

    private fun handleHintClick() {
        if (binding.imageCardView.visibility == View.VISIBLE || binding.imagehint.visibility == View.VISIBLE) {
            toggleHintVisibility()
        } else {
            showHintDialog()
        }
    }

    private fun showHintDialog() {
        MaterialDialog(this)
            .cornerRadius(20f)
            .title(text = "View Hint")
            .message(text = "Watch an ad to get your hint")
            .positiveButton(text = "Continue") {
                binding.progressBar.visibility = View.VISIBLE
                Log.d(myTag, "Button Clicked")
                loadRewardedAd()
            }
            .negativeButton(text = "Back") { it.dismiss() }
            .show()
    }

    private fun loadRewardedAd() {
//        val adRequest = AdRequest.Builder().build()
//
//        RewardedAd.load(this, "ca-app-pub-8438387212221904/5784077781", adRequest,
//            object : RewardedAdLoadCallback() {
//                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                    binding.progressBar.visibility = View.INVISIBLE
//                    binding.imageCardView.visibility = View.VISIBLE
//                    binding.imagehint.visibility = View.VISIBLE
//                    Log.d(myTag, loadAdError.message)
//                    mRewardedAd = null
//                    Log.d(myTag, "onAdFailedToLoad")
//                }
//
//                override fun onAdLoaded(rewardedAd: RewardedAd) {
//                    mRewardedAd = rewardedAd
//                    Log.d(myTag, "Ad is Loaded")
//                    showRewardedAd()
//                    mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
//                        override fun onAdShowedFullScreenContent() {
//                            binding.progressBar.visibility = View.INVISIBLE
//                            Log.d(myTag, "Ad was shown.")
//                            mRewardedAd = null
//                        }
//
//                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//                            binding.progressBar.visibility = View.INVISIBLE
//                            Log.d(myTag, "Ad failed to show.")
//                            toggleHintVisibility()
//                        }
//
//                        override fun onAdDismissedFullScreenContent() {
//                            binding.progressBar.visibility = View.INVISIBLE
//                            Log.d(myTag, "Ad was dismissed.")
//                        }
//                    }
//                }
//            })
    }

    private fun showRewardedAd() {
//        if (mRewardedAd != null) {
//            mRewardedAd?.show(this) {
//                // Handle the reward
//                binding.progressBar.visibility = View.INVISIBLE
//                binding.imageCardView.visibility = View.VISIBLE
//                binding.imagehint.visibility = View.VISIBLE
//            }
//        } else {
//            binding.progressBar.visibility = View.INVISIBLE
//            toggleHintVisibility()
//            Log.d(myTag, "The rewarded ad wasn't ready yet.")
//        }
    }

    private fun returnRandomWord(): Int {
        val randomInt = (1 until alphabetNameArray.size).random()
        correctAnswers.add(alphabetNameArray[randomInt])
        return randomInt
    }

    // ... (rest of the methods like loadRewardedAd, showRewardedAd, returnRandomWord remain the same)

    override fun setVisibility(visibility: Int, guideTextView: Int) {
        findViewById<TextView>(guideTextView).visibility = View.INVISIBLE
    }

    override fun setVisibilityAnimSwipe(visibility: Int, lottieAnime: Int) {
        findViewById<LottieAnimationView>(lottieAnime).visibility = visibility
    }

    override fun setVisibilityAnim(visibility: Int, lottieAnimeSuccess: Int) {
        findViewById<LottieAnimationView>(lottieAnimeSuccess).visibility = View.VISIBLE
    }

    override fun changeText(View: Int) {
        // Implementation remains the same
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onResume() {
        super.onResume()
        game.isLooping = true
        game.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        game.release()
    }

    override fun onPause() {
        super.onPause()
        game.pause()
    }

    override fun onBackPressed() {
        if (binding.imageCardView.visibility == View.VISIBLE || binding.imagehint.visibility == View.VISIBLE) {
            toggleHintVisibility()
        } else {
            super.onBackPressed()
        }
    }
}