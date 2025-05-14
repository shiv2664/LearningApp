package com.shivam.learningapp.game3alphabet

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.shivam.learningapp.DashboardActivity
import com.shivam.learningapp.R
import com.shivam.learningapp.databinding.ActivityAlphabetsPlagroundBinding
import es.dmoral.toasty.Toasty

class AlphabetsPlayground : AppCompatActivity() {


    var correctAnswers:ArrayList<String> = ArrayList()
    private lateinit var alphabetImageArray:ArrayList<Int>
    private lateinit var alphabetNameArray:ArrayList<String>
    private lateinit var downloadedOptions:ArrayList<String>
    var ans =""
    var marks : Int = 0
    var correct : Int = 0
    var no = 1
    //        textView7.text= no.toString()
    var flag = 0
    var correctOptions:String=""
    private lateinit var hyperspaceJumpAnimation:Animation
    private lateinit var shake:Animation
    var level:String?=null
    private lateinit var game: MediaPlayer
    
    private lateinit var binding: ActivityAlphabetsPlagroundBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAlphabetsPlagroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {


            level = intent.getStringExtra("level")
            game = MediaPlayer.create(this@AlphabetsPlayground, R.raw.bg)
            if (DashboardActivity.volumeMain) {
                game.isLooping = true
            }
            var number: Int = 1


//       val extras :Bundle=Bundle()
//        extras.putString("max_ad_content_rating", "G")
//        val request :AdRequest = AdRequest.Builder().
//        addNetworkExtrasBundle(AdMobAdapter::class.java,extras).build();

//            val mAdView = adView
//            val adRequest = AdRequest.Builder().build()
//            mAdView.loadAd(adRequest)

            volume.setOnClickListener {
                if (game.isPlaying) {
                    DashboardActivity.volumeMain = false
                    game.pause()
                    volume.setBackgroundResource(R.drawable.voloff)
                } else if (!game.isPlaying) {
                    DashboardActivity.volumeMain = true
                    game.isLooping = true
                    volume.setBackgroundResource(R.drawable.volon)
                    game.start()
                }
            }


            hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this@AlphabetsPlayground, R.anim.left_enter_views)
            shake = AnimationUtils.loadAnimation(this@AlphabetsPlayground, R.anim.shake)



            alphabetImageArray = arrayListOf<Int>(
                R.mipmap.a,
                R.mipmap.a1,
                R.mipmap.a2,
                R.mipmap.a3,
                R.mipmap.b,
                R.mipmap.b1,
                R.mipmap.b2,
                R.mipmap.b3,
                R.mipmap.c,
                R.mipmap.c1,
                R.mipmap.c2,
                R.mipmap.c3,
                R.mipmap.d,
                R.mipmap.d1,
                R.mipmap.d2,
                R.mipmap.d3,
                R.mipmap.e,
                R.mipmap.e1,
                R.mipmap.e2,
                R.mipmap.e3,
                R.mipmap.f,
                R.mipmap.f1,
                R.mipmap.f2,
                R.mipmap.f3,
                R.mipmap.g,
                R.mipmap.g1,
                R.mipmap.g2,
                R.mipmap.g3,
                R.mipmap.h,
                R.mipmap.h1,
                R.mipmap.h2,
                R.mipmap.h3,
                R.mipmap.i,
                R.mipmap.i1,
                R.mipmap.i2,
                R.mipmap.i3,
                R.mipmap.j,
                R.mipmap.j1,
                R.mipmap.j2,
                R.mipmap.j3,
                R.mipmap.k,
                R.mipmap.k1,
                R.mipmap.k2,
                R.mipmap.k3,
                R.mipmap.l,
                R.mipmap.l1,
                R.mipmap.l2,
                R.mipmap.l3,
                R.mipmap.m,
                R.mipmap.m1,
                R.mipmap.m2,
                R.mipmap.m3,
                R.mipmap.n,
                R.mipmap.n1,
                R.mipmap.n2,
                R.mipmap.n3,
                R.mipmap.o,
                R.mipmap.o1,
                R.mipmap.o2,
                R.mipmap.o3,
                R.mipmap.p,
                R.mipmap.p1,
                R.mipmap.p2,
                R.mipmap.p3,
                R.mipmap.q,
                R.mipmap.q1,
                R.mipmap.q2,
                R.mipmap.q3,
                R.mipmap.r,
                R.mipmap.r1,
                R.mipmap.r2,
                R.mipmap.r3,
                R.mipmap.s,
                R.mipmap.s1,
                R.mipmap.s2,
                R.mipmap.s3,
                R.mipmap.t,
                R.mipmap.t1,
                R.mipmap.t2,
                R.mipmap.t3,
                R.mipmap.u,
                R.mipmap.u1,
                R.mipmap.u2,
                R.mipmap.u3,
                R.mipmap.v,
                R.mipmap.v1,
                R.mipmap.v2,
                R.mipmap.v3,
                R.mipmap.w,
                R.mipmap.w1,
                R.mipmap.w2,
                R.mipmap.w3,
                R.mipmap.x,
                R.mipmap.x1,
                R.mipmap.x2,
                R.mipmap.x3,
                R.mipmap.y,
                R.mipmap.y1,
                R.mipmap.y2,
                R.mipmap.y3,
                R.mipmap.z,
                R.mipmap.z1,
                R.mipmap.z2,
                R.mipmap.z3,
            )

            alphabetNameArray = arrayListOf<String>(
                "apple",
                "airplane",
                "ant",
                "axe",
                "banana",
                "bat",
                "box",
                "bed",
                "cat",
                "cow",
                "cart",
                "candle",
                "doctor",
                "doll",
                "drum",
                "duck",
                "elephant",
                "eggs",
                "envelope",
                "engine",
                "Fire truck",
                "fan",
                "frog",
                "fire",
                "guitar",
                "grapes",
                "gift",
                "girl",
                "horse",
                "hen",
                "Hut",
                "heart",
                "ice cream",
                "ice",
                "island",
                "igloo",
                "Jar",
                "jeans",
                "jam",
                "jet",
                "kite",
                "kid",
                "key",
                "king",
                "ladder",
                "lion",
                "leaf",
                "lemon",
                "mango",
                "monkey",
                "mat",
                "mug",
                "nest",
                "net",
                "needle",
                "nose",
                "owl",
                "orange",
                "onion",
                "ostrich",
                "panda",
                "pen",
                "pigeon",
                "pencil",
                "queen",
                "question",
                "quiet",
                "quilt",
                "rabbit",
                "rainbow",
                "rose",
                "rocket",
                "socks",
                "sun",
                "sunflower",
                "soap",
                "turtle",
                "tiger",
                "teapot",
                "truck",
                "umbrella",
                "umpire",
                "uniform",
                "upupa",
                "volleyball",
                "vegetables",
                "violin",
                "volcano",
                "wolf",
                "watch",
                "watermelon",
                "window",
                "xylophone",
                "X-ray",
                "Xerox Machine",
                "X-Mas Tree",
                "yoyo",
                "yellow",
                "yoga",
                "yak",
                "zebra",
                "zip",
                "zoo",
                "zeppelin"
            )

            downloadedOptions = arrayListOf(
                "silk",
                "bath",
                "sign",
                "bare",
                "nutritious",
                "late",
                "earn",
                "spray",
                "talented",
                "thinkable",
                "jolly",
                "cloth",
                "kaput",
                "scarce",
                "crowd",
                "stupid",
                "shaky",
                "belong",
                "chief",
                "heady",
                "ultra",
                "grape",
                "productive",
                "develop",
                "tray",
                "feeble",
                "separate",
                "wary",
                "threatening",
                "twig",
                "challenge",
                "top",
                "freezing",
                "flimsy",
                "optimal",
                "error",
                "bruise",
                "peaceful",
                "homely",
                "perfect",
                "shivering",
                "risk",
                "spell",
                "note",
                "tiresome",
                "cow",
                "crowded",
                "card",
                "precede",
                "subdued",
                "staking",
                "useful",
                "coat",
                "ashamed",
                "lively",
                "time",
                "position",
                "sulky",
                "mask",
                "skillful",
                "nail",
                "suit",
                "quartz",
                "march",
                "perpetual",
                "brainy",
                "synonymous",
                "division",
                "profuse",
                "bag",
                "weather",
                "second-hand",
                "cloistered",
                "existence",
                "crime",
                "educated",
                "aware",
                "broad",
                "knot",
                "tramp",
                "gold",
                "old-fashioned",
                "scattered",
                "cows",
                "dependent",
                "tire",
                "plan",
                "vessel",
                "troubled",
                "unlock",
                "screeching",
                "decay",
                "occur",
                "dead",
                "pricey",
                "throne",
                "soup",
                "claim",
                "play",
                "finger",
                "remind",
                "fumbling",
                "unsuitable",
                "tremble",
                "snails",
                "telephone",
                "even",
                "boorish",
                "weary",
                "nose",
                "farm",
                "acoustics",
                "minute",
                "grubby",
                "cream",
                "lopsided",
                "concentrate",
                "spoil",
                "manage",
                "rhythm",
                "brick",
                "shake",
                "dolls",
                "rude",
                "heavenly",
                "rhetorical",
                "hanging",
                "rabbit",
                "marry",
                "shrill",
                "milk",
                "hissing",
                "four",
                "weight",
                "direful",
                "trip",
                "necessary",
                "lackadaisical",
                "ruthless",
                "berry",
                "pets",
                "ready",
                "noise",
                "reward",
                "giant",
                "taste",
                "common",
                "desert",
                "hobbies",
                "sound",
                "ripe",
                "impossible",
                "birth",
                "grade",
                "language",
                "save",
                "cat",
                "talk",
                "treat",
                "skin",
                "hilarious",
                "mom",
                "shave",
                "vein",
                "capable",
                "rapid",
                "fail",
                "motionless",
                "oval",
                "jelly",
                "pause",
                "examine",
                "ocean",
                "wonder",
                "copy",
                "magical",
                "frogs",
                "parched",
                "knowledgeable",
                "eggnog",
                "queen",
                "unknown",
                "pass",
                "public",
                "floor",
                "belief",
                "ceaseless",
                "true",
                "middle",
                "ink",
                "change",
                "sea",
                "condemned",
                "available",
                "snore",
                "puffy",
                "scared",
                "purring",
                "materialistic",
                "great"
            )


            val random1 = (1 until alphabetImageArray.size).random()
            val random2 = (1 until alphabetImageArray.size).random()
            val random3 = (1 until alphabetImageArray.size).random()
            val options1 = downloadedOptions[random1]
            val options2 = downloadedOptions[random2]
            var options3 = downloadedOptions[random3]


            correctOptions = alphabetNameArray[random1]
            correctAnswers.add(correctOptions)
            imageviewAlphabet.setBackgroundResource(alphabetImageArray[random1])

            val random = (1..4).random()

            if (random == 1) {
                optionA.text = correctOptions
                optionB.text = options3
                optionC.text = options1
                optionD.text = options2

            } else if (random == 2) {
                optionA.text = options2
                optionB.text = correctOptions
                optionC.text = options3
                optionD.text = options1
            } else if (random == 3) {
                optionA.text = options2
                optionB.text = options3
                optionC.text = correctOptions
                optionD.text = options1
            } else if (random == 4) {
                optionA.text = options2
                optionB.text = options3
                optionC.text = options1
                optionD.text = correctOptions
            }

            buttonCard1A.setOnClickListener {
                selectedAnswer(buttonCard1A, buttona, optionA, buttonb, buttonc, buttond)
            }
            buttonCard2B.setOnClickListener {
                selectedAnswer(buttonCard2B, buttonb, optionB, buttona, buttonc, buttond)
            }
            buttonCard3C.setOnClickListener {
                selectedAnswer(buttonCard3C, buttonc, optionC, buttonb, buttona, buttond)
            }
            buttonCard4D.setOnClickListener {
                selectedAnswer(buttonCard4D, buttond, optionD, buttonc, buttona, buttonb)
            }

        }

    }

    fun selectedAnswer(cardView: CardView,button: TextView,textView: TextView,button2:TextView,button3: TextView,button4: TextView){

        ans = textView.text.toString()
        button.setBackgroundResource(R.drawable.circlegreen)
        button.setTextColor(resources.getColor(R.color.colorPrimaryDark))
//
        button2.setBackgroundResource(R.drawable.circlepurple)
        button2.setTextColor(resources.getColor(R.color.white))

        button3.setBackgroundResource(R.drawable.circlepurple)
        button3.setTextColor(resources.getColor(R.color.white))

        button4.setBackgroundResource(R.drawable.circlepurple)
        button4.setTextColor(resources.getColor(R.color.white))
        clear()
        nextQues(cardView,ans)

    }

    fun nextQues(cardView: CardView,ans:String){

        binding.apply {

            val sharedPref = getSharedPreferences("LevelCompletedStatus", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()

            if (ans == "") {
                Toasty.info(this@AlphabetsPlayground, "you need to select one answer", Toasty.LENGTH_SHORT, true).show()
            } else if (ans == correctOptions) {
                correct++
                textView7.text=(correct*20).toString()
            }

            if (ans!="") {

                buttonCard1A.startAnimation(hyperspaceJumpAnimation)
                buttonCard2B.startAnimation(hyperspaceJumpAnimation)
                buttonCard3C.startAnimation(hyperspaceJumpAnimation)
                buttonCard4D.startAnimation(hyperspaceJumpAnimation)
                cardView.startAnimation(hyperspaceJumpAnimation)
                imageviewAlphabet.startAnimation(hyperspaceJumpAnimation)

                flag++
                ansClear()
                correctOptions=""

                if (level == "G3B1Lv1E1"&&flag == 5){
                    marks = correct*20
                    Log.d("MyTag", "Marks are : $marks")
                    if (marks>=60) {
                        editor.putBoolean("G3B1Lv1E1", true)
                        editor.apply()
                    }
                    val intent = Intent(this@AlphabetsPlayground, G3ResultActivity::class.java)
                    intent.putExtra("Game","AlphabetsPlayground")
                    intent.putExtra("marks",marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers",correctAnswers)
                    startActivity(intent)
                    finish()
                }else if (level == "G3B1Lv1E2"&&flag == 5){
                    marks = correct*20
                    Log.d("MyTag","Marks are : "+marks)
                    if (marks>=60) {
                        editor.putBoolean("G3B1Lv1E2", true)
                        editor.apply()
                    }
                    val intent = Intent(this@AlphabetsPlayground, G3ResultActivity::class.java)
                    intent.putExtra("Game","AlphabetsPlayground")
                    intent.putExtra("marks",marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers",correctAnswers)
                    startActivity(intent)
                    finish()
                }else if (level == "G3B1Lv1E3"&&flag == 5){
                    marks = correct*20
                    if (marks>=60) {
                        editor.putBoolean("G3B1Lv1E3", true)
                        editor.apply() }
                    Log.d("MyTag","Marks are : "+marks)
                    val intent = Intent(this@AlphabetsPlayground, G3ResultActivity::class.java)
                    intent.putExtra("Game","AlphabetsPlayground")
                    intent.putExtra("marks",marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers",correctAnswers)
                    startActivity(intent)
                    finish()
                }
                else{

                    val random1 = (1 until alphabetImageArray.size).random()
                    val random2 = (1 until alphabetImageArray.size).random()
                    val random3 = (1 until alphabetImageArray.size).random()
                    val options1 = downloadedOptions[random1]
                    val options2 = downloadedOptions[random2]
                    var options3 = downloadedOptions[random3]


                    correctOptions = alphabetNameArray[random1]
                    correctAnswers.add(correctOptions)
                    imageviewAlphabet.setBackgroundResource(alphabetImageArray[random1])

                    val random = (1..4).random()

                    if (random == 1) {
                        optionA.text = correctOptions
                        optionB.text = options3
                        optionC.text = options1
                        optionD.text = options2

                    } else if (random == 2) {
                        optionA.text = options2
                        optionB.text = correctOptions
                        optionC.text = options3
                        optionD.text = options1
                    } else if (random == 3) {
                        optionA.text = options2
                        optionB.text = options3
                        optionC.text = correctOptions
                        optionD.text = options1
                    } else if (random == 4) {
                        optionA.text = options2
                        optionB.text = options3
                        optionC.text = options1
                        optionD.text = correctOptions
                    }

                    no++

//                    textView7.text = no.toString()
                    Log.d("MyTag", " no are : $no")
                    clear()
                }

            }
            else{
                cardView.startAnimation(shake)

            }
            
        }
        


    }

    override fun onResume() {
        super.onResume()
        game.isLooping = true
        game.start()
    }

    override fun onDestroy() {
        super.onDestroy()
//        adapter1.clearItems()
//        adapter2.clearItems()
//        recycler_view_1.adapter=null
    }

    override fun onPause() {
        super.onPause()
        game.pause()
//        adapter1.clearItems()
//        adapter2.clearItems()
    }

    fun ansClear(){
        ans=""
    }

    fun clear(){
        binding.apply {

            buttona.setBackgroundResource(R.drawable.circlepurple)
            buttona.setTextColor(resources.getColor(R.color.white))
//
            buttonb.setBackgroundResource(R.drawable.circlepurple)
            buttonb.setTextColor(resources.getColor(R.color.white))

            buttonc.setBackgroundResource(R.drawable.circlepurple)
            buttonc.setTextColor(resources.getColor(R.color.white))

            buttond.setBackgroundResource(R.drawable.circlepurple)
            buttond.setTextColor(resources.getColor(R.color.white))

        }
    }
}