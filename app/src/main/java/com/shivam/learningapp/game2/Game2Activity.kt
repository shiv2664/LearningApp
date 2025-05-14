package com.shivam.learningapp.game2

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.shivam.learningapp.R
import com.shivam.learningapp.api.MyGame2WebService
import com.shivam.learningapp.databinding.ActivityGame2Binding
import com.shivam.learningapp.game2.beginner.G2LevelsActivityBeginners
import com.shivam.learningapp.game2.beginner.G2ResultActivity
import com.shivam.learningapp.model.Game2Model
import com.shivam.learningapp.utils.MyBounceInterpolator
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Game2Activity : AppCompatActivity() {

    private lateinit var click: MediaPlayer
    var arrayQuiz: ArrayList<String> = ArrayList()
    var game2Data: Game2Model? = null
    private lateinit var getQuestions: Call<Game2Model?>
    var preDefinedOptionsArray: ArrayList<String> = ArrayList()
    var questions: ArrayList<String> = ArrayList()
    var correctAnswers: ArrayList<String> = ArrayList()
    private lateinit var game: MediaPlayer

    var question = ""
    var a = ""
    var b = ""
    var c = ""
    var d = ""
    var answer = ""

    private lateinit var binding: ActivityGame2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGame2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.left_enter_views)
        val hyperspaceJumpAnimation2 = AnimationUtils.loadAnimation(this, R.anim.right_enter_views)

        val level = intent.getStringExtra("level")
//        Log.d("MyTag", "level is $level")

        binding.apply {


            game = MediaPlayer.create(this@Game2Activity, R.raw.gamesound)
            game.isLooping = true

            volumeButton.setOnClickListener {
                if (game.isPlaying) {
                    game.pause()
                    volumeButton.setBackgroundResource(R.drawable.voloff)
                } else if (!game.isPlaying) {
                    volumeButton.setBackgroundResource(R.drawable.volon)
                    game.start()
                }
            }

            var marks: Int = 0
            var correct: Int = 0
            var no = 1
            textView7.text = no.toString()
            var ans = ""
            var flag = 0

            val mWebService = MyGame2WebService.retrofit?.create(MyGame2WebService::class.java)

            val downloadedOptions = arrayListOf(
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




            if (level == "B1Lv1E1") {


                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 1, "es"
                )!!
            } else if (level == "B1Lv1E2") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 2, "es"
                )!!

            } else if (level == "B1Lv1E3") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 3, "es"
                )!!

            } else if (level == "B1Lv2E1") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 4, "es"
                )!!

            } else if (level == "B1Lv2E2") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 4, "es"
                )!!

            } else if (level == "B1Lv2E3") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 4, "es"
                )!!

            } else if (level == "B1Lv3E1") {

                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 5, "es"
                )!!
            } else if (level == "B1Lv3E2") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 5, "es"
                )!!
            } else if (level == "B1Lv3E3") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 5, "es"
                )!!
            } else if (level == "B1Lv4E1") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 6, "es"
                )!!
            } else if (level == "B1Lv4E2") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 6, "es"
                )!!
            } else if (level == "B1Lv4E3") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 7, "es"
                )!!
            } else if (level == "B1Lv5E1") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 7, "es"
                )!!
            } else if (level == "B1Lv5E2") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 8, "es"
                )!!
            } else if (level == "B1Lv5E3") {
                getQuestions = mWebService?.getQuestions(
                    "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
                    "twinword-word-association-quiz.p.rapidapi.com", 10, "es"
                )!!
            }



            getQuestions.enqueue(object : Callback<Game2Model?> {
                override fun onResponse(call: Call<Game2Model?>, response: Response<Game2Model?>) {
                    if (response.isSuccessful) {

                        game2Data = response.body()

                        lottieloadinggame.visibility = View.GONE
                        lottieanimmonkey.visibility = View.VISIBLE
                        linearlayout.visibility = View.VISIBLE
                        buttonCard1A.startAnimation(hyperspaceJumpAnimation)
                        buttonCard2B.startAnimation(hyperspaceJumpAnimation)
                        buttonCard3C.startAnimation(hyperspaceJumpAnimation)
                        buttonCard4D.startAnimation(hyperspaceJumpAnimation)
                        lottieanimmonkey.startAnimation(hyperspaceJumpAnimation)
                        back.startAnimation(hyperspaceJumpAnimation)
                        next.startAnimation(hyperspaceJumpAnimation2)

                        if (game2Data != null) {

//                       val preDefinedOptions = game2Data!!.quizlist.flatMap { q -> q.option }


                            a = game2Data!!.quizlist[flag].option[0]
                            b = game2Data!!.quizlist[flag].option[1]

                            val correctPosition = game2Data!!.quizlist[flag].correct
                            val random1 = (1 until downloadedOptions.size).random()
                            val random2 = (1 until downloadedOptions.size).random()
                            val random3 = (1 until downloadedOptions.size).random()
                            val options1 = downloadedOptions[random1]
                            val options2 = downloadedOptions[random2]
                            var options3 = downloadedOptions[random3]

                            Question.text = game2Data!!.quizlist[flag].quiz.toString()
                            questions.add(game2Data!!.quizlist[flag].quiz.toString())
                            correctAnswers.add(game2Data!!.quizlist[flag].option[game2Data!!.quizlist[flag].correct - 1])

                            val random = (1..4).random()

                            if (random == 1) {
                                optionA.text = game2Data!!.quizlist[flag].option[0]
                                optionB.text = game2Data!!.quizlist[flag].option[1]
                                optionC.text = options1
                                optionD.text = options2

                            } else if (random == 2) {
                                optionA.text = options2
                                optionB.text = game2Data!!.quizlist[flag].option[0]
                                optionC.text = game2Data!!.quizlist[flag].option[1]
                                optionD.text = options1
                            } else if (random == 3) {
                                optionA.text = options2
                                optionB.text = game2Data!!.quizlist[flag].option[1]
                                optionC.text = game2Data!!.quizlist[flag].option[0]
                                optionD.text = options1
                            } else if (random == 4) {
                                optionA.text = options2
                                optionB.text = game2Data!!.quizlist[flag].option[1]
                                optionC.text = options1
                                optionD.text = game2Data!!.quizlist[flag].option[0]
                            }
                        }

                    } else {
                        Log.d("MyTag", "" + response.code().toString())
                        Toasty.info(
                            this@Game2Activity,
                            "Network Error please try again",
                            Toasty.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Game2Model?>, t: Throwable) {
                    Log.d("MyTag", "Throwables is : " + t.localizedMessage)
                }
            })

            var myAnim: Animation? = null
            myAnim = AnimationUtils.loadAnimation(this@Game2Activity, R.anim.bounce)
            click = MediaPlayer.create(this@Game2Activity, R.raw.bubble)

            buttonCard1A.setOnClickListener {
                ans = optionA.text.toString()
                buttona.setBackgroundResource(R.drawable.circlegreen)
                buttona.setTextColor(resources.getColor(R.color.colorPrimaryDark))
//
                buttonb.setBackgroundResource(R.drawable.circlepurple)
                buttonb.setTextColor(resources.getColor(R.color.white))

                buttonc.setBackgroundResource(R.drawable.circlepurple)
                buttonc.setTextColor(resources.getColor(R.color.white))

                buttond.setBackgroundResource(R.drawable.circlepurple)
                buttond.setTextColor(resources.getColor(R.color.white))
            }

            buttonCard2B.setOnClickListener {
                ans = optionB.text.toString()

                buttonb.setBackgroundResource(R.drawable.circlegreen)
                buttonb.setTextColor(resources.getColor(R.color.colorPrimaryDark))
//
                buttona.setBackgroundResource(R.drawable.circlepurple)
                buttona.setTextColor(resources.getColor(R.color.white))

                buttonc.setBackgroundResource(R.drawable.circlepurple)
                buttonc.setTextColor(resources.getColor(R.color.white))

                buttond.setBackgroundResource(R.drawable.circlepurple)
                buttond.setTextColor(resources.getColor(R.color.white))
            }
            buttonCard3C.setOnClickListener {
                ans = optionC.text.toString()

                buttonc.setBackgroundResource(R.drawable.circlegreen)
                buttonc.setTextColor(resources.getColor(R.color.colorPrimaryDark))
//
                buttonb.setBackgroundResource(R.drawable.circlepurple)
                buttonb.setTextColor(resources.getColor(R.color.white))

                buttona.setBackgroundResource(R.drawable.circlepurple)
                buttona.setTextColor(resources.getColor(R.color.white))

                buttond.setBackgroundResource(R.drawable.circlepurple)
                buttond.setTextColor(resources.getColor(R.color.white))
            }
            buttonCard4D.setOnClickListener {
                ans = optionD.text.toString()

                buttond.setBackgroundResource(R.drawable.circlegreen)
                buttond.setTextColor(resources.getColor(R.color.colorPrimaryDark))
//
                buttonb.setBackgroundResource(R.drawable.circlepurple)
                buttonb.setTextColor(resources.getColor(R.color.white))

                buttonc.setBackgroundResource(R.drawable.circlepurple)
                buttonc.setTextColor(resources.getColor(R.color.white))

                buttona.setBackgroundResource(R.drawable.circlepurple)
                buttona.setTextColor(resources.getColor(R.color.white))
            }

            back.setOnClickListener {

                back.startAnimation(myAnim)
                click.start()
                // Use bounce interpolator with amplitude 0.2 and frequency 20
                val interpolator = MyBounceInterpolator(0.2, 20.0)
                myAnim.interpolator = interpolator
                myAnim.duration = 1000

                val intent = Intent(this@Game2Activity, G2LevelsActivityBeginners::class.java)
                startActivity(intent)

            }



            next.setOnClickListener {

                if (ans == "") {
                    Toasty.info(this@Game2Activity, "you need to select one answer", Toasty.LENGTH_SHORT, true)
                        .show()
                } else if (ans == game2Data!!.quizlist[flag].option[game2Data!!.quizlist[flag].correct - 1]) {
                    correct++
                }

                if (ans != "") {

                    buttonCard1A.startAnimation(hyperspaceJumpAnimation)
                    buttonCard2B.startAnimation(hyperspaceJumpAnimation)
                    buttonCard3C.startAnimation(hyperspaceJumpAnimation)
                    buttonCard4D.startAnimation(hyperspaceJumpAnimation)

                    flag++
                    ans = ""
                    if (level == "B1Lv1E1" && flag == 5) {
                        marks = correct * 20

                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)
                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "1")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv1E1")
                        intent.putExtra("gameLevel", "B1Lv1")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv1E2" && flag == 5) {
                        marks = correct * 20


                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)
                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "2")
                        intent.putExtra("progressLevel", "B1Lv1E2")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("gameLevel", "B1Lv1")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv1E3" && flag == 5) {
                        marks = correct * 20
                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)

                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "3")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv1E3")
                        intent.putExtra("gameLevel", "B1Lv1")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv2E1" && flag == 5) {
                        marks = correct * 20


                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)
                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "2")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv2E1")
                        intent.putExtra("gameLevel", "B1Lv2")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv2E2" && flag == 5) {
                        marks = correct * 20

                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)

                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "3")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv2E2")
                        intent.putExtra("gameLevel", "B1Lv2")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv2E3" && flag == 5) {
                        marks = correct * 20

                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)

                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "3")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv2E3")
                        intent.putExtra("gameLevel", "B1Lv2")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv3E1" && flag == 5) {
                        marks = correct * 20

                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)
                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "2")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv3E1")
                        intent.putExtra("gameLevel", "B1Lv3")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv3E2" && flag == 5) {
                        marks = correct * 20

                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)

                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "3")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv3E2")
                        intent.putExtra("gameLevel", "B1Lv3")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv3E3" && flag == 5) {
                        marks = correct * 20

                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)

                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "3")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv3E3")
                        intent.putExtra("gameLevel", "B1Lv3")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv4E1" && flag == 5) {
                        marks = correct * 20


                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)
                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "2")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv4E1")
                        intent.putExtra("gameLevel", "B1Lv4")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv4E2" && flag == 5) {
                        marks = correct * 20

                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)


                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "3")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv4E2")
                        intent.putExtra("gameLevel", "B1Lv4")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv4E3" && flag == 5) {
                        marks = correct * 20

                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)


                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "3")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv4E3")
                        intent.putExtra("gameLevel", "B1Lv4")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv5E1" && flag == 5) {
                        marks = correct * 20


                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)
                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "2")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv5E1")
                        intent.putExtra("gameLevel", "B1Lv5")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv5E2" && flag == 5) {
                        marks = correct * 20

                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)

                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "3")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv5E2")
                        intent.putExtra("gameLevel", "B1Lv5")
                        startActivity(intent)
                        finish()
                    } else if (level == "B1Lv5E3" && flag == 5) {
                        marks = correct * 20

                        val intent = Intent(this@Game2Activity, G2ResultActivity::class.java)

                        intent.putExtra("marks", marks)
                        intent.putExtra("level", "3")
                        intent.putStringArrayListExtra("questions", questions)
                        intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                        intent.putExtra("progressLevel", "B1Lv5E3")
                        intent.putExtra("gameLevel", "B1Lv5")
                        startActivity(intent)
                        finish()
                    } else {

                        val random1 = (1 until downloadedOptions.size).random()
                        val random2 = (1 until downloadedOptions.size).random()
                        val random3 = (1 until downloadedOptions.size).random()
                        val options1 = downloadedOptions[random1]
                        val options2 = downloadedOptions[random2]
                        val options3 = downloadedOptions[random3]

                        Question.text = game2Data!!.quizlist[flag].quiz.toString()
                        questions.add(game2Data!!.quizlist[flag].quiz.toString())
                        correctAnswers.add(game2Data!!.quizlist[flag].option[game2Data!!.quizlist[flag].correct - 1])

                        val random = (1..4).random()

                        if (random == 1) {
                            optionA.text = game2Data!!.quizlist[flag].option[0]
                            optionB.text = game2Data!!.quizlist[flag].option[1]
                            optionC.text = options1
                            optionD.text = options2

                        } else if (random == 2) {
                            optionA.text = options2
                            optionB.text = game2Data!!.quizlist[flag].option[1]
                            optionC.text = game2Data!!.quizlist[flag].option[0]
                            optionD.text = options1
                        } else if (random == 3) {
                            optionA.text = options2
                            optionB.text = game2Data!!.quizlist[flag].option[0]
                            optionC.text = game2Data!!.quizlist[flag].option[1]
                            optionD.text = options1
                        } else if (random == 4) {
                            optionA.text = options2
                            optionB.text = game2Data!!.quizlist[flag].option[0]
                            optionC.text = options1
                            optionD.text = game2Data!!.quizlist[flag].option[1]
                        }
                        no++

                        textView7.text = no.toString()
                        Log.d("MyTag", " no are : $no")
                        clear()
                    }
                }

                Log.d("MyTag", "correct are : $correct")
//            Log.d("MyTag", " no are : $")
            }
        }

    }

    override fun onPostResume() {
        super.onPostResume()
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

    fun clear() {
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