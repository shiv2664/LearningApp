package com.shivam.learningapp.game1

import android.content.Intent
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.shivam.learningapp.DashboardActivity
import com.shivam.learningapp.R
import com.shivam.learningapp.adapters.CustomAdapter
import com.shivam.learningapp.adapters.DragListener
import com.shivam.learningapp.api.MyGame1WebService
import com.shivam.learningapp.databinding.ActivityAgeBindingImpl
import com.shivam.learningapp.databinding.ActivityGame1Binding
import com.shivam.learningapp.game1.beginner.ResultsActivity
import com.shivam.learningapp.interfaces.CustomListener
import com.shivam.learningapp.model.Game1Model
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Game1Activity : AppCompatActivity(), CustomListener {

    val myTag = "MyTag"

    companion object {

        var checkSuccess: Boolean = false
        var stringList: MutableList<String>? = null
        lateinit var indianCultureList: MutableList<String>
        lateinit var sentenceOptions: MutableList<String>


        var notShuffleArrayList: ArrayList<String> = ArrayList()
        var shuffleArrayList: ArrayList<String> = ArrayList()
        var shuffleArrayList2: ArrayList<String> = ArrayList(shuffleArrayList.size)

    }

    var correctAnswers: ArrayList<String> = ArrayList()
    lateinit var factsList: List<String>
    lateinit var lastValues: List<String>
    lateinit var randomSentences: List<String>
    lateinit var kidsSentences: List<String>

    private lateinit var game: MediaPlayer

    private lateinit var adapter1: CustomAdapter
    private lateinit var adapter2: CustomAdapter
    private lateinit var sentencesCall: Call<Game1Model>
//    private var mRewardedAd: RewardedAd? = null

    private lateinit var binding: ActivityGame1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGame1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            val hyperspaceJumpAnimation =
                AnimationUtils.loadAnimation(this@Game1Activity, R.anim.left_enter_views)
            val hyperspaceJumpAnimation2 =
                AnimationUtils.loadAnimation(this@Game1Activity, R.anim.right_enter_views)

            if (!isNetworkAvailable()) {
                game = MediaPlayer.create(this@Game1Activity, R.raw.bg)
                game.isLooping = false
                game.pause()
                relativeLayout.visibility = View.INVISIBLE
                Toasty.info(
                    this@Game1Activity,
                    "No Network Please connect your device to Internet",
                    Toasty.LENGTH_SHORT,
                    true
                ).show()
            } else {
                game = MediaPlayer.create(this@Game1Activity, R.raw.bg)
                if (DashboardActivity.volumeMain) {
                    game.isLooping = true

                }
            }



            volume.setOnClickListener {
                if (game.isPlaying) {
                    game.pause()
                    DashboardActivity.volumeMain = false
                    volume.setBackgroundResource(R.drawable.voloff)
                } else if (!game.isPlaying) {
                    volume.setBackgroundResource(R.drawable.volon)
                    DashboardActivity.volumeMain = false
                    game.start()
                }
            }

            val level = intent.getStringExtra("level")
            Log.d("MyTag", "level is $level")

            var marks: Int = 0
            var correct: Int = 0
            var no = 1
            var ans = ""
            var flag = 1

            textView7.text = no.toString()

            randomSentences = arrayListOf(
                "I love apples.",
                "I ate the apple.",
                "Tom loved bananas.",
                "Bananas are yellow.",
                "Cat is a very cute animal.",
                "Cats nails are sharp.",
                "An apple a day keep the doctor away.",
                "Doctors treat sick people.",
                "Elephant color is gray.",
                "Elephants like to eat every day.",
                "Fire trucks are red in color.",
                "Fire trucks hs two doors.",
                "I want a guitar.",
                "I play the guitar.",
                "Tom has a horse.",
                "Horses eat grass.",
                "I love ice cream.",
                "Give me ice cream.",
                "Jam comes in a jar.",
                "The jar is empty. ",
                "Let's fly kites.",
                "He flew a kite.",
                "We need a ladder.",
                "He needs a ladder.",
                "Mango is the national fruit of India",
                "Mango is a seasonal fruit",
                "Nest is built by the bird",
                "The bird loves her nest",
                "Owls have sharp, curved beaks",
                "Owls have sharp talons",
                "Pandas are in the bear family",
                "Pandas live in the bamboo forest",
                "The queen rolls her eyes",
                "I am the proud queen",
                "Rabbit is a small animal",
                "Rabbit has two eyes",
                "He wears clean socks every day.",
                "He was wearing odd socks.",
                "Turtle is the slowest animal.",
                "Turtle has the hardest shell.",
                "Umbrella protects us from rain.",
                "I have an umbrella.",
                "Children watched volleyball.",
                "You can also play volleyball.",
                "Your wolf loves me.",
                "You are the most beautiful wolf.",
                "Mukesh like a xylophone.",
                "this@Game1Activity is a xylophone.",
                "I like to play YOYO.",
                "I love apples.",
                "I ate the apple.",
                "Tom loved bananas.",
                "Bananas are yellow.",
                "Cat is a very cute animal.",
                "Cats nails are sharp.",
                "An apple a day keep the doctor away.",
                "Doctors treat sick people.",
                "Elephant color is gray.",
                "Elephants like to eat every day.",
                "Fire trucks are red in color.",
                "Fire trucks hs two doors.",
                "I want a guitar.",
                "I play the guitar.",
                "Tom has a horse.",
                "Horses eat grass. ",
                "I love ice cream.",
                "Give me ice cream.",
                "Jam comes in a jar.",
                "The jar is empty.",
                "Let's fly kites.",
                "He flew a kite.",
                "We need a ladder.",
                "He needs a ladder.",
                "Mango is the national fruit of India",
                "Mango is a seasonal fruit",
                "Nest is built by the bird",
                "The bird loves her nest",
                "Owls have sharp, curved beaks",
                "Owls have sharp talons",
                "Pandas are in the bear family",
                "Pandas live in the bamboo forest",
                "The queen rolls her eyes",
                "I am the proud queen",
                "Rabbit is a small animal",
                "Rabbit has two eyes",
                "He wears clean socks every day.",
                "He was wearing odd socks.",
                "Turtle is the slowest animal.",
                "Turtle has the hardest shell.",
                "Umbrella protects us from rain.",
                "I have an umbrella.",
                "Children watched volleyball.",
                "You can also play volleyball.",
                "Your wolf loves me.",
                "You are the most beautiful wolf.",
                "Mukesh like a xylophone.",
                "this@Game1Activity is a xylophone.",
                "I like to play YOYO.",
                "YOYO is fun.",
                "Zebra has four legs.",
                "Zebra can not eat meat.",
                "He hurt his knee when he fell.",
                "She pointed her finger at him accusingly.",
                "Thanks. I'll pay you back on Friday.",
                "It wasn't necessary for him to bring an umbrella.",
                "You look so pale.",
                "Would you care for more coffee?",
                "He burned himself badly.",
                "I don't agree with him.",
                "My university has a dorm.",
                "She said she had a slight cold.",
                "Do I have to do it over again?",
                "I can hardly swim.",
                "May I see your insurance card, please?",
                "I'm very sad to hear that.",
                "How many barbers work in that barbershop?",
                "He narrowly escaped being run over.",
                "Just a little.",
                "She thought that he was wasting electricity.",
                "I'm afraid we don't have any left.",
                "Is it close?",
                "Thank you for your letter.",
                "What's your favorite sport?",
                "The boy is kind.",
                "That customer came back to complain again.",
                "Do you want to come along?",
                "When I came home, Tom was studying.",
                "He doesn't lie.",
                "Did you enjoy your winter holidays?",
                "I had a healthy breakfast.",
                "Don't lose sleep over that.",
                "The sky was clear when I left home.",
                "It is dark in that room.",
                "I couldn't go out on account of the bad weather.",
                "You must be back by 10 o'clock.",
                "I want to get in touch with him.",
                "We play on Sunday.",
                "I didn't go to school yesterday.",
                "It is going to rain soon.",
                "She is a beauty.",
                "He goes to the office by car.",
                "He gave me a good piece of advice.",
                "I called him a coward to his face.",
                "I'll be busy next week.",
                "What is the area code?",
                "Don't oppose him.",
                "Tom lives in the room above us.",
                "My sister is having a baby in June.",
                "There's very little we can do now.",
                "this@Game1Activity is the calm before the storm.",
                "I'll write or phone you next week.",
                "It is no use asking me for money.",
                "She isn't kind to him.",
                "I arrived there too early.",
                "I didn't want to surprise you.",
                "We had less snow this@Game1Activity winter than we had expected.",
                "Pop. It's Mariah Carey.",
                "I want to go to college.",
                "He made up his mind to jog in spite of his blindness.",
                "Oh, I've heard that's a good movie. What time does it start?",
                "Which CD do you want to listen to?",
                "All the boys fell in love with Julia.",
                "Well, if you don't have any plans, your family could have dinner at my house.",
                "My family will be away for a week.",
                "Do I have to take this@Game1Activity medicine?",
                "We won't be able to arrive home in time.",
                "No one told me that she had failed.",
                "Look! Are those all your clothes on the couch?",
                "She's at the hotel now.",
                "I don't think there's one around here. The closest one is on 3rd street, but that's probably closed now.",
                "She agreed to my idea.",
                "You have to work harder to make up for lost time.",
                "OK. What are you going to have to eat?",
                "I feel like going for a walk this@Game1Activity morning.",
                "Both of those students didn't pass the test.",
                "Who are they?",
                "How about a cup of tea?",
                "She bought a handkerchief for ten dollars.",
                "I've never been this@Game1Activity happy before.",
                "Don't throw garbage away here.",
                "It was only yesterday that I realized what she really meant.",
                "What size?",
                "I'll be back at ten.",
                "I went, too.",
                "You'll find our house at the end of the next street.",
                "He was absent at roll call.",
                "She helped her younger brother finish his picture.",
                "It was her that told me.",
                "She grinned at me when she came into the room.",
                "I hate myself for not having the willpower to quit eating junk food.",
                "Tom saw something red there.",
                "Parents are responsible for their children's education.",
                "When will they arrive?",
                "My father is tall.",
                "Hey, Pam what's up?",
                "With Christmas just around the corner, I should do some shopping.",
                "Tom held a seat for me.",
                "When are you busy?",
                "It is obvious that he is right.",
                "She may not be aware of the danger.",
                "We have a good heating system.",
                "Which tooth hurts?",
                "I do not need money now.",
                "You can see many animals in this@Game1Activity forest.",
                "She traveled around Japan.",
                "She took my hand.",
                "I usually take a shower after I play tennis, but today I couldn't.",
                "My uncle works in this@Game1Activity office.",
                "I am disappointed that my friend is not here.",
                "Keep off the grass.",
                "Is the job too much for you?",
                "this@Game1Activity racket belongs to me.",
                "Does Nancy want to have a dog?",
                "People have the tendency to speak more loudly when they get excited.",
                "Let me take care of that for you.",
                "Yes, that's fine. It works well outside.",
                "What time do you have?",
                "How high is Mt. Fuji?",
                "She grew up to be a famous doctor.",
                "He cannot have done such a thing.",
                "When did it begin to rain?",
                "She sat next to him and listened quietly.",
                "When are you coming back?",
                "I stayed up all night.",
                "She begged him to stay.",
                "I will accept full responsibility for this@Game1Activity.",
                "I am excited at the prospect of seeing her.",
                "I called him a coward to his face.",
                "I think you have too many clothes.",
                "Don't cry. Everything will be OK.",
                "He kept me waiting.",
                "He designed the car.",
                "She bought him a ticket.",
                "That's a school closed early.",
                "He reached his goal.",
                "this@Game1Activity is what he said.",
                "He should have arrived by now.",
                "She abandoned her children.",
                "I couldn't answer any questions on the test.",
                "Canada is north of here.",
                "My father quickly scanned the newspaper.",
                "If you follow this@Game1Activity street, you will get to the station.",
                "He gets mad very easily.",
                "She looks nice and healthy.",
                "I didn't use to like wine, but now I like it a lot.",
                "I usually get up at eight.",
                "Your way of looking at something depends on your situation.",
                "What will you use it for?",
                "Is she American?",
                "Give me a bottle of wine.",
                "this@Game1Activity heater burns gas."
            )

            kidsSentences = arrayListOf(
                "He hurt his knee when he fell.",
                "She pointed her finger at him accusingly.",
                "Thanks. I'll pay you back on Friday.",
                "It wasn't necessary for him to bring an umbrella.",
                "You look so pale.",
                "Would you care for more coffee?",
                "He burned himself badly.",
                "I don't agree with him.",
                "My university has a dorm.",
                "She said she had a slight cold.",
                "Do I have to do it over again?",
                "I can hardly swim.",
                "May I see your insurance card, please?",
                "I'm very sad to hear that.",
                "How many barbers work in that barbershop?",
                "He narrowly escaped being run over.",
                "Just a little.",
                "She thought that he was wasting electricity.",
                "I'm afraid we don't have any left.",
                "Is it close?",
                "Thank you for your letter.",
                "What's your favorite sport?",
                "The boy is kind.",
                "That customer came back to complain again.",
                "Do you want to come along?",
                "When I came home, Tom was studying.",
                "He doesn't lie.",
                "Did you enjoy your winter holidays?",
                "I had a healthy breakfast.",
                "Don't lose sleep over that.",
                "The sky was clear when I left home.",
                "It is dark in that room.",
                "I couldn't go out on account of the bad weather.",
                "You must be back by 10 o'clock.",
                "I want to get in touch with him.",
                "We play on Sunday.",
                "I didn't go to school yesterday.",
                "It is going to rain soon.",
                "She is a beauty.",
                "He goes to the office by car.",
                "He gave me a good piece of advice.",
                "I called him a coward to his face.",
                "I'll be busy next week.",
                "What is the area code?",
                "Don't oppose him.",
                "Tom lives in the room above us.",
                "My sister is having a baby in June.",
                "There's very little we can do now.",
                "this@Game1Activity is the calm before the storm.",
                "I'll write or phone you next week.",
                "It is no use asking me for money.",
                "She isn't kind to him.",
                "I arrived there too early.",
                "I didn't want to surprise you.",
                "We had less snow this@Game1Activity winter than we had expected.",
                "Pop. It's Mariah Carey.",
                "I want to go to college.",
                "He made up his mind to jog in spite of his blindness.",
                "Oh, I've heard that's a good movie. What time does it start?",
                "Which CD do you want to listen to?",
                "All the boys fell in love with Julia.",
                "Well, if you don't have any plans, your family could have dinner at my house.",
                "My family will be away for a week.",
                "Do I have to take this@Game1Activity medicine?",
                "We won't be able to arrive home in time.",
                "No one told me that she had failed.",
                "Look! Are those all your clothes on the couch?",
                "She's at the hotel now.",
                "I don't think there's one around here. The closest one is on 3rd street, but that's probably closed now.",
                "She agreed to my idea.",
                "You have to work harder to make up for lost time.",
                "OK. What are you going to have to eat?",
                "I feel like going for a walk this@Game1Activity morning.",
                "Both of those students didn't pass the test.",
                "Who are they?",
                "How about a cup of tea?",
                "She bought a handkerchief for ten dollars.",
                "I've never been this@Game1Activity happy before.",
                "Don't throw garbage away here.",
                "It was only yesterday that I realized what she really meant.",
                "What size?",
                "I'll be back at ten.",
                "I went, too.",
                "You'll find our house at the end of the next street.",
                "He was absent at roll call.",
                "She helped her younger brother finish his picture.",
                "It was her that told me.",
                "She grinned at me when she came into the room.",
                "I hate myself for not having the willpower to quit eating junk food.",
                "Tom saw something red there.",
                "Parents are responsible for their children's education.",
                "When will they arrive?",
                "My father is tall.",
                "Hey, Pam what's up?",
                "With Christmas just around the corner, I should do some shopping.",
                "Tom held a seat for me.",
                "When are you busy?",
                "It is obvious that he is right.",
                "She may not be aware of the danger.",
                "We have a good heating system.",
                "Which tooth hurts?",
                "I do not need money now.",
                "You can see many animals in this@Game1Activity forest.",
                "She traveled around Japan.",
                "She took my hand.",
                "I usually take a shower after I play tennis, but today I couldn't.",
                "My uncle works in this@Game1Activity office.",
                "I am disappointed that my friend is not here.",
                "Keep off the grass.",
                "Is the job too much for you?",
                "this@Game1Activity racket belongs to me.",
                "Does Nancy want to have a dog?",
                "People have the tendency to speak more loudly when they get excited.",
                "Let me take care of that for you.",
                "Yes, that's fine. It works well outside.",
                "What time do you have?",
                "How high is Mt. Fuji?",
                "She grew up to be a famous doctor.",
                "He cannot have done such a thing.",
                "When did it begin to rain?",
                "She sat next to him and listened quietly.",
                "When are you coming back?",
                "I stayed up all night.",
                "She begged him to stay.",
                "I will accept full responsibility for this@Game1Activity.",
                "I am excited at the prospect of seeing her.",
                "I called him a coward to his face.",
                "I think you have too many clothes.",
                "Don't cry. Everything will be OK.",
                "He kept me waiting.",
                "He designed the car.",
                "She bought him a ticket.",
                "That's a school closed early.",
                "He reached his goal.",
                "this@Game1Activity is what he said.",
                "He should have arrived by now.",
                "She abandoned her children.",
                "I couldn't answer any questions on the test.",
                "Canada is north of here.",
                "My father quickly scanned the newspaper.",
                "If you follow this@Game1Activity street, you will get to the station.",
                "He gets mad very easily.",
                "She looks nice and healthy.",
                "I didn't use to like wine, but now I like it a lot.",
                "I usually get up at eight.",
                "Your way of looking at something depends on your situation.",
                "What will you use it for?",
                "Is she American?",
                "Give me a bottle of wine.",
                "this@Game1Activity heater burns gas."
            )


            factsList = arrayListOf(
                "About 1.2 billion people live in India.",
                "The capital of India is New Delhi",
                "The largest city of India is Mumbai.",
                "The currency in India is the rupee.",
                "The main religion in India is Hinduism",
                "Cows are seen as sacred in India",
                "most people are vegetarians in India",
                "India is the world’s largest democracy.",
                "The name ‘India’ derives from the river Indus.",
                "India has the second-largest population in the world.",
                "India is the 7th largest country in the world.",
                "Thousands of languages are spoken all over India.",
                "The national symbol of India is the Bengal Tiger.",
                "India is the second-largest English speaking country in the world.",
                "Hinduism, the world’s oldest religion",
                "India gained independence from Britain in 1947.",
                "Chess was invented in India",
                "India is the world’s largest producer of milk.",
                "India has over 300,000 mosques.",
                "Varanasi is one of the oldest inhabited places.",
                "The Rig Veda is the oldest known book in the world.",
                "Hinduism is the third largest religion in the world",
                "Hindu belief says that gods can take many forms",
                "Sanskrit is the commonly used language in Hindu texts",
                "There is no single founder of Hinduism",
                "Hinduism real name is Sanātana Dharma",
                "Hinduism encourages a vegetarian diet",
                "Hindus believe in Karma",
                "Yoga is a vital part of Hinduism",
                "The Kumbh Mela is the largest spiritual gathering in the world",
                "Million of Hindus worship cows",
                "Ayurveda is a science of medicine.",
                "Lord Brahma, Lord Vishnu, and Lord Shiva are the main deities in Hinduism",
                "The Indian flap shell turtle are omnivorous.",
                "Panipat town is known for its traditional hand-loom industry",
                "The Ganges River flows through two countries in Asia.",
                "The Ganges River ends when it flows into the Bay of Bengal",
                "The Ganges River is the longest river in India.",
                "The Ganges River is the 4th longest river in Bangladesh.",
                "The Ganges River is the 35th longest river in the world.",
                "India is a country in Asia.",
                "India covers 1,269,219 square miles.",
                "In India, cars are driven on the left side.",
                "Sanskrit is the commonly used language in Hindu texts",
                "India never invaded any country.",
                "The Himalayas were virtually impassable.",
                "Babur won the First Battle of Panipat.",
                "Aditi is the mother of the Vamana avatar of Vishnu.",
                "According to Hindu mythology, Brahma is the creator.",
                "According to Hindu mythology, Vishnu is the preserver",
                "According to Hindu mythology, Shiva is the destroyer.",
                "Gods bowed down in obeisance before Shiva.",
                "Valmiki is the writer of the Ramayana.",
                "Mahabharata was originally known as Jaya(‘Jayam’)",
                "The youngest Padava, Sahdev could see the future",
                "Duryodhana’s real name was Suyodhana",
                "In Mahabharata, Vidur was the avatar of Yamraj",
                "Bhishma Pitamah’s real name was Devavrata",
                "It was a Brahmana’s curse that killed Karna",
                "Balram was the father in law of Abhimanyu",
                "The first battle of Kurukshetra was not Mahabharata",
                "In Mahabharata Ekalavya was actually Krishna’s cousin",
                "Zero was invented by Aryabhatta.",
                "The decimal system was developed in India.",
                "There are more than 100 styles of yoga.",
                "Yoga is over 5,000 years old.",
                "Yoga is now actively promoted as a sport.",
                "Yoga boosts your immune system.",
                "Swami Vivekananda was born on 12 January 1863",
                "Swami Vivekananda died on 4 July 1902.",
                "Rajasthan has a Temple of Rats",
                "India was the first country to mine diamonds.",
                "India has 22 recognized languages.",
                "India has the highest population of vegetarians.",
                "The world’s largest sundial is located in India.",
                "The Hindu calendar has six seasons",
                "India is divided into 29 states.",
                "70% of the world’s spices come from India.",
                "India has the tallest statue in the world.",
                "India was the first country to refine sugar.",
                "The word “shampoo” comes from the word `champu`.",
                "There’s a floating post office in India",
                "Mahatma Gandhi's mother tongue was Gujarati.",
                "Mahatma Gandhi did his schooling from Rajkot.",
                "Gandhi was responsible for the Civil Rights movement",
                "Gandhi was nominated for the Nobel Peace prize",
                "Steve Jobs round glasses were tribute to Mahatma Gandhi"

            )

            Log.d(myTag, "Array List Size is :" + factsList.size)

            indianCultureList = arrayListOf<String>(
                "Local mafia indulged in dacoity and kidnapping.",
                "Murder and bank dacoity, arson are rare.",
                "About 1.2 billion people live in India.",
                "The capital of India is New Delhi",
                "The largest city of India is Mumbai.",
                "The currency in India is the rupee.",
                "The main religion in India is Hinduism",
                "Cows are seen as sacred in India",
                " most people are vegetarians in India",
                "India is the world’s largest democracy.",
                "The name ‘India’ derives from the river Indus.",
                "India has the second-largest population in the world.",
                "India is the 7th largest country in the world.",
                "Thousands of languages are spoken all over India.",
                "The national symbol of India is the Bengal Tiger.",
                "India is the second largest English speaking country in the world.",
                "Hinduism, the world’s oldest religion",
                "India gained independence from Britain in 1947.",
                "Chess was invented in India",
                "India is the world’s largest producer of milk.",
                "India has over 300,000 mosques.",
                "Varanasi is one of the oldest inhabited places.",
                "The Rig Veda is the oldest known book in the world.",
                "Hinduism the third largest religion in the world",
                "Hindu belief says that gods can take many forms",
                "Sanskrit is the commonly used language in Hindu texts",
                "There is no single founder of Hinduism",
                "Hinduism real name is Sanātana Dharma",
                "Hinduism encourages a vegetarian diet",
                "Hindus believe in Karma",
                "Yoga is a vital part of Hinduism",
                "The Kumbh Mela is the largest spiritual gathering in the world",
                "Million of Hindus worship cows",
                "Ayurveda is a science of medicine.",
                "Lord Brahma, Lord Vishnu, and Lord Shiva are the main deities in Hinduism",
                "The Indian flapshell turtle are omnivorous.",
                "Panipat town is known for its traditional handloom industry",
                "The Ganges River flows through two countries in Asia.",
                "The Ganges River ends when it flows into the Bay of Bengal",
                "The Ganges River is the longest river in India.",
                "The Ganges River is the 4th longest river in Bangladesh.",
                "The Ganges River is the 35th longest river in the world.",
                "India is a country in Asia.",
                "India covers 1,269,219 square miles.",
                "In India, cars are driven on the left side.",
                "In an ordinary dacoity, much money is not involved.",
                "this@Game1Activity encounter ended over two years of urban dacoity and crime by him.",
                "But many members of the community are still involved in dacoity.",
                "A month passed after the Kakori Dacoity, and yet no one was arrested.",
                "Until recently, the area was known for dacoity, but it is now safe for tourists.",
                "My grandpa was the founder of the American Indian Movement.",
                "The word settled in the vocabulary of the american indian population.",
                "In 1930 he published the concluding volume of The North American Indian.",
                "The proliferation of ethnic cats serves to undermine the Indian American cat.",
                "Lodgepole pine is named for its common use in the American Indian tepee lodge.",
                "The Indian soldiers capitulated.",
                "The building adjoins the Indian Fields.",
                "Indian rebels recant the brutal slayings.",
                "The Indian flapshell turtle are omnivorous.",
                "Anna is an Indian type of copper.",
                "Take the Mutiny as the datum line.",
                "The exhausted troops mutiny against the Emperor.",
                "He was acquitted of the charge of mutiny.",
                "The chronometer attained fame because of the mutiny.",
                "French troops stationed in the country quelled the mutiny.",
                "The immediate issues of the mutiny were conditions and food.",
                "this@Game1Activity was essentially the impasse which led to the mutiny.",
                "Aftershock 2005 is the fourth album by the Funk band Mutiny.",
                "The leader of the mutiny was executed and the rest imprisoned.",
                "The chronometer attained fame because of the mutiny on the Bounty.",
                "The local newspaper is the Lucknow Sentinel.",
                "Lucknow remains as a suburb of Bairnsdale.",
                "Sitapur district is a part of Lucknow division.",
                "He was present at the capture of Lucknow.",
                "By now, Lucknow was openly mutinous.",
                "Havelock decided to attempt to relieve Lucknow.",
                "There are the famous eateries of Lucknow in the market.",
                "It was not said that Kipling edited the pioneer at Lucknow.",
                "Lucknow is the administrative headquarters of the division.",
                "Hardoi district is a part of Lucknow division.",
                "The three famous battles of Panipat took place near the modern town of Panipat.",
                "Panipat and Kurukshetra are not correct.",
                "Safidon is the center of panipat and jind.",
                "India census, Panipat had a population of .",
                "An oil refinery is being set up at Panipat.",
                "It is located on the panipat jind railway line.",
                "Panipat town is known for its traditional handloom industry.",
                "And it is situated in south side of panipat.",
                "Babur defeated the Lodi sultan decisively at the First Battle of Panipat.",
                "Panipat was the scene of three pivotal battles in Indian history.",
                "Chapati is another lunch item preferred nowadays.",
                "Chapati or chapatti is a type of roti or Indian bread.",
                "The hot air cooks the chapati rapidly from the inside.",
                "Roll out maida balls to make small un cooked Chapati.",
                "The breakfast may be paratha, of chapati with fried eggs or halua.",
                "It is also used for cooking chapati in almost every Indian home.",
                "Often, the top of a chapati is slathered with butter or ghee clarified butter .",
                "Idolatry is the core pillar of Hinduism.",
                "Thus, Hinduism is alike kabalistic Judaism in being pantheistic.",
                "Thus in Hinduism, there is no choice of avocation.",
                "Brahman is the transcendent and immanent Ultimate Reality of Hinduism.",
                "The majority of the population of the district practise Hinduism.",
                "Can the experts in the Hinduism project opine on this@Game1Activity? ",
                "In fact, the multifarious deities within Hinduism pose a false polytheism.",
                "Second, the question of chronology in Hinduism is not solvable.",
                "Hinduism, on the other hand does not recognize the existence of apostasy.",
                "The fatalist view of reality is only a fragmentary part of Hinduism.",
                "She is declared chaste by the village Panchayat.",
                "The bank caters the financial need of the Farmers in Ulickal Panchayat.",
                "The Sarpanch or Chairperson is the head of the Gram Panchayat.",
                "The first Shariat panchayat of Punjab scheduled to be inaugurated on 3 March 2013.",
                "The panchayat would be Pathanapuram panchayat.",
                "My brother in law is a panchayat member of the Martalli panchayat.",
                "The Gram Panchayat is the foundation of the Panchayat System.",
                "Pinarayi grama panchayat was chosen as the best panchayat in the district.",
                "The Panchayat Samiti is the link between the Gram Panchayat and Zilla Parishad.",
                "Plan for grama panchayat/municipality.",
                "He fell in love with the gambling in the Taj Mahal Casino.",
                "The Taj mahal has a reverberating dome.",
                "The Jats also ransacked the villages, set aside for the support of Taj Mahal.",
                "The tower in the back is the modern extension of the Taj Mahal Hotel.",
                "The Jats also ransacked the villages set aside for the support of Taj Mahal.",
                "The Taj is overrated by the way.",
                "The minarets display the Taj Mahal's penchant for symmetry.",
                "The panoramic view from the roof of the Mahal is stunning.",
                "The evidence of the history of the Taj is overwhelming.",
                "The Firangi Mahal led and steered the Muslims of India.",
                "He is the writer of the Ramayana.",
                "Ravana was also the villain of the immortal epic, the Ramayana.",
                "The great epic of Ramayana was transferred orally for hundreds of generations.",
                "At the time of Ramayana, the state of unconsciousness was regarded as death.",
                "I am a particularly avid fan of both the Ramayana and the Mahabharata.",
                "It just talks about the Ramayana.",
                "The 'Mahabharata' corroborates the Ramayana account.",
                "And great work on the Ramayana article.",
                "Mandodari's role is short in the Ramayana.",
                "It is merely the view expressed in Ramayana.",
                "I am a particularly avid fan of both the Ramayana and the Mahabharata.",
                "Mahabharata and the Indian caste system.",
                "Mahabharata does not mention this@Game1Activity date.",
                "It has a history from Mahabharata.",
                "The 'Mahabharata' corroborates the Ramayana account.",
                "The Critical Edition of the Mahabharata.",
                "It is said to be home of vidhur of mahabharata.",
                "In Mahabharata many such weapons are described.",
                "The full purport of the Mahabharata is also there.",
                "Mahabharata also reckons the technological knowledge of the Yavanas.",
                "The boy went to bathe in river Ganges.",
                "Hindus visit the Mandir and bathe in the Holy waters of the Ganges.",
                "The image was sprinkled with the water of the Ganges, and thrown into the pit.",
                "The settlement lies in the Ganges Delta.",
                "The Ganges forms the northern boundary of the district.",
                "There are two major dams on the Ganges.",
                "It is situated on the top of the delta of Ganges.",
                "The ashram is located on the bank of the Ganges.",
                "He settled on the banks of the Ganges.",
                "They are the traditional ferrymen on the Ganges.",
                "Being obstructed by the Himalayas, the region receives heavy rainfall.",
                "Mandi town fall in the lower most climatic zone of the Himalayas.",
                "The site has a fantastic views of the Himalayas and the city.",
                "The musk deer and the brown bear is found in the higher Himalayas.",
                "The rivers of the Himalayas are snow fed and so perennial throughout the year.",
                "Panoramic views of the Himalayas.",
                "Ecology and man in the Himalayas.",
                "The daidai originated in the Himalayas.",
                "It's about as obvious as the Himalayas.",
                "They bowed down in obeisance before Shiva.",
                "When Shiva requests her to disgorge him, she obliges.",
                "Shiva is a part of the customs for bereavement in Judaism.",
                "Shiva participated in the nonviolent Chipko movement during the 1970s.",
                "Shiva is devastated at the news and leaves the room quietly.",
                "Shiva is out to make a statement against the venal forces.",
                "The ancient Shiva Temple is at a bosom of this@Game1Activity village.",
                "Lord Shiva agreed to help them and waited for an opportune moment.",
                "It is a salutation to God in the form of Lord Shiva.",
                "Parvati drew the attention of Shiva to the presence of the sage.",
                "Goddess Parvati is worshipped by seekers of conjugal bliss and happiness.",
                "There is an open glade by the banks of the river Parvati.",
                "Parvati drew the attention of Shiva to the presence of the sage.",
                "Parvati, dressed in her finery with her hair falling to the front, looks away.",
                "Parvati then won that immunity challenge.",
                "Parvati is avidly dedicated to physical fitness.",
                "Parvati is the most common name.",
                "The pose is reminiscent of the goddess Parvati.",
                "Nandi was adopted as a son by Parvati.",
                "She was an incarnation of the goddess Parvati.",
                "They moved downstream by the Brahmaputra.",
                "Hi I made a map of the Brahmaputra.",
                "The Indus and Brahmaputra rise in the south.",
                "The Brahmaputra valley is not almost at sea level.",
                "Nevertheless, he discovered the sources of the Indus and the Brahmaputra.",
                "Shambhuganj is situated on the other side of the Brahmaputra.",
                "His soldiers rallied and a desperate battle ensured on the Brahmaputra.",
                "Goalpara is located by the bank of Brahmaputra",
                "The Meghna represents the accumulated waters of the Brahmaputra and Ganges.",
                "Vishnu holding the conch represents him as the god of sound.",
                "Vishnu is reclining at the bottom of the ocean.",
                "The preserver of all the creation is titled as Vishnu.",
                "The portrayal of the characters by Vishnu and Arathi was excellent.",
                "Aditi also is the mother of the Vamana avatar of Vishnu.",
                "Alakshmi is the elder sister of the goddess Lakshmi",
                "Brahma is the creator, Vishnu is the preserver and I am the destroyer.",
                "The idol is made up of 10008 Saligram that compose the reclining Vishnu.",
                "Vishnu the preserver.",
                "The lotus started to tremble and Brahma was showered with drops of water.",
                "Brahma is the creator.",
                " Vishnu is the preserver.",
                "Shiva is the destroyer.",
                "As he sat in blissful contentment, the god Brahma approached and bowed to him.",
                "Brahma and Outburst are clearly down.",
                "View espoused in the Brahma Yamala.",
                "Brahma is important but not popular.",
                "Narada and Brahma are also pictured.",
                "Brahma decided to intervene.",
                "The gods fled in desperation to Brahma."
            )


            sentenceOptions = arrayListOf(
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

//        firstSentence()
            val lastValues = randomSentences[randomReturnSentence1()].split(" ").map { it.trim() }
            secondSentence(lastValues)

            nextBTn.setOnClickListener {
                progressBarGame.visibility = View.INVISIBLE
                hintTextView.visibility = View.INVISIBLE

                if (DragListener.correct) {
                    correct++
                    DragListener.correct = false
                }

                Log.d(myTag, "Correct is : $correct")



                Log.d(myTag, "flag before increment : $flag")

                flag++
                no++
                textView7.text = no.toString()

                Log.d(myTag, "correct answers are : $correctAnswers")

                if (level == "G1B1Lv1E1" && flag == 6) {
                    marks = correct * 20


                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putExtra("progressLevel", "G1B1Lv1E1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("gameLevel", "G1B1Lv1")
                    startActivity(intent)
                    finish()
                } else if (level == "G1B1Lv1E2" && flag == 6) {

                    marks = correct * 20

                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv1E2")
                    intent.putExtra("gameLevel", "G1B1Lv1")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv1E3" && flag == 6) {

                    marks = correct * 20

                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv1E3")
                    intent.putExtra("gameLevel", "G1B1Lv1")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv2E1" && flag == 6) {

                    marks = correct * 20
                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv2E1")
                    intent.putExtra("gameLevel", "G1B1Lv2")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv2E2" && flag == 6) {

                    marks = correct * 20

                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv2E2")
                    intent.putExtra("gameLevel", "G1B1Lv2")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv2E3" && flag == 6) {

                    marks = correct * 20

                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv2E3")
                    intent.putExtra("gameLevel", "G1B1Lv2")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv3E1" && flag == 6) {

                    marks = correct * 20

                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv3E1")
                    intent.putExtra("gameLevel", "G1B1Lv3")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv3E2" && flag == 6) {

                    marks = correct * 20


                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv3E2")
                    intent.putExtra("gameLevel", "G1B1Lv3")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv3E3" && flag == 6) {

                    marks = correct * 20

                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv3E3")
                    intent.putExtra("gameLevel", "G1B1Lv3")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv4E1" && flag == 6) {

                    marks = correct * 20


                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv4E1")
                    intent.putExtra("gameLevel", "G1B1Lv4")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv4E2" && flag == 6) {

                    marks = correct * 20

                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv4E2")
                    intent.putExtra("gameLevel", "G1B1Lv4")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv4E3" && flag == 6) {

                    marks = correct * 20

                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv4E3")
                    intent.putExtra("gameLevel", "G1B1Lv4")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv5E1" && flag == 6) {

                    marks = correct * 20

                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv5E1")
                    intent.putExtra("gameLevel", "G1B1Lv5")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv5E2" && flag == 6) {

                    marks = correct * 20

                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv5E2")
                    intent.putExtra("gameLevel", "G1B1Lv5")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else if (level == "G1B1Lv5E3" && flag == 6) {

                    marks = correct * 20


                    val intent = Intent(this@Game1Activity, ResultsActivity::class.java)
                    intent.putExtra("marks", marks)
                    intent.putExtra("level", "1")
                    intent.putStringArrayListExtra("correctAnswers", correctAnswers)
                    intent.putExtra("progressLevel", "G1B1Lv5E3")
                    intent.putExtra("gameLevel", "G1B1Lv5")
//                Log.d(myTag, "correct are :$correct")
                    startActivity(intent)
                    finish()

                } else {

                    if (flag == 2) {
//                    val lastValues = factsList[randomReturn()].split(" ").map { it.trim() }
//                    secondSentence(lastValues)
                        val lastValues =
                            randomSentences[randomReturnSentence1()].split(" ").map { it.trim() }
                        secondSentence(lastValues)

                    } else if (flag == 3) {
//                    firstSentence()
                        val lastValues =
                            randomSentences[randomReturnSentence1()].split(" ").map { it.trim() }
                        secondSentence(lastValues)


                    } else if (flag == 4) {
//                    val lastValues = factsList[randomReturn()].split(" ").map { it.trim() }
//                    secondSentence(lastValues)
                        val lastValues =
                            randomSentences[randomReturnSentence1()].split(" ").map { it.trim() }
                        secondSentence(lastValues)
                    } else if (flag == 5) {
//                    firstSentence()
                        val lastValues =
                            randomSentences[randomReturnSentence1()].split(" ").map { it.trim() }
                        secondSentence(lastValues)

                    }
                }

            }

            hintTextView.text = "Word at 3rd position is " + notShuffleArrayList[2]
//        hintTextView.visibility=View.INVISIBLE
            nextHint.setOnClickListener {

                if (hintTextView.visibility == View.VISIBLE) {
                    hintTextView.visibility = View.INVISIBLE
                    progressBarGame.visibility = View.INVISIBLE
                } else if (hintTextView.visibility == View.INVISIBLE) {

                    val dialog = MaterialDialog(this@Game1Activity)
                        .cornerRadius(20f)
                        .title(text = "View Hint")
                        .message(text = "Watch an ad to get your hint")
                        .positiveButton(text = "Continue") { dialog ->
                            // Do something
                            progressBarGame.visibility = View.VISIBLE
                            Log.d(myTag, "Button Clicked")
                            loadRewardedAd()
                        }
                        .negativeButton(text = "Back") { dialog ->
                            // Do something
                            dialog.dismiss()
                        }
                    dialog.show()
                }

//            Log.d(myTag,"Button CLicked")
//
//            if ( hintTextView.visibility==View.INVISIBLE) {
//                progressBarGame.visibility = View.VISIBLE
//                loadRewardedAd()
//            }else if ( hintTextView.visibility==View.VISIBLE) {
//                hintTextView.visibility=View.INVISIBLE
//                progressBarGame.visibility = View.INVISIBLE
//            }
            }

        }
    }

    // sample   ca-app-pub-3940256099942544/5224354917
    private fun loadRewardedAd() {
//        val adRequest: AdRequest = AdRequest.Builder().build()
//
//        binding.apply {
//            RewardedAd.load(
//                this@Game1Activity,
//                "ca-app-pub-8438387212221904/8573892652",
//                adRequest,
//                object : RewardedAdLoadCallback() {
//                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                        // Handle the error.
//                        binding.progressBarGame.visibility = View.INVISIBLE
//                        Log.d(myTag, loadAdError.message)
//                        hintTextView.visibility = View.VISIBLE
//
//                        mRewardedAd = null
//                        Log.d(myTag, "onAdFailedToLoad")
//                    }
//
//                    override fun onAdLoaded(rewardedAd: RewardedAd) {
//                        mRewardedAd = rewardedAd
//                        Log.d(myTag, "Ad is Loaded")
//                        showRewardedAd()
//                        mRewardedAd!!.fullScreenContentCallback = object :
//                            FullScreenContentCallback() {
//                            override fun onAdShowedFullScreenContent() {
//                                // Called when ad is shown.
//                                progressBarGame.visibility = View.INVISIBLE
//                                Log.d(myTag, "Ad was shown.")
//                                mRewardedAd = null
//
//                            }
//
//                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//                                // Called when ad fails to show.
//                                progressBarGame.visibility = View.INVISIBLE
//                                Log.d(myTag, "Ad failed to show.")
//                                hintTextView.visibility = View.VISIBLE
//
//                            }
//
//                            override fun onAdDismissedFullScreenContent() {
//                                // Called when ad is dismissed.
//                                // Don't forget to set the ad reference to null so you
//                                // don't show the ad a second time.
//                                progressBarGame.visibility = View.INVISIBLE
//                                Log.d(myTag, "Ad was dismissed.")
////                            loadRewardedAd()
//                            }
//                        }
//                    }
//                })
//        }
//
//


    }

    private fun showRewardedAd() {
//        if (mRewardedAd != null) {
//            mRewardedAd!!.show(this@Game1Activity) {
//                // Handle the reward.
//                binding.progressBarGame.visibility = View.INVISIBLE
//                binding.hintTextView.visibility = View.VISIBLE
//
//            }
//        } else {
//            binding.progressBarGame.visibility = View.INVISIBLE
//            binding.hintTextView.visibility = View.VISIBLE
//            Log.d(myTag, "The rewarded ad wasn't ready yet.")
//        }
    }


    private fun randomReturnSentence1(): Int {
        val randomInt = (1 until randomSentences.size).random()
        val lastValues = randomSentences[randomInt].split(" ").map { it.trim() }

        if (lastValues.size <= 8) {
            correctAnswers.add(randomSentences[randomInt])
            return randomInt
        } else {
            return randomReturnSentence1()
        }
    }

    private fun randomReturn(): Int {

        val randomInt = (1 until factsList.size).random()
        val lastValues = factsList[randomInt].split(" ").map { it.trim() }

        if (lastValues.size <= 8) {
            correctAnswers.add(factsList[randomInt])
            return randomInt
        } else {
            return randomReturn()
        }

    }

    private fun randomReturnApi(): Int {

        return (1 until sentenceOptions.size).random()

    }

    fun firstSentence() {
        val mWebService = MyGame1WebService.retrofit?.create(MyGame1WebService::class.java)

        val sentencesCall2 = mWebService?.getSentences(
            "d504747de4msh889d2d59ccb1747p136375jsn46e0c449ec42",
            "wordsapiv1.p.rapidapi.com",
            sentenceOptions[randomReturnApi()]
        )

        notShuffleArrayList.clear()
        shuffleArrayList.clear()
        shuffleArrayList2.clear()

        sentencesCall2?.enqueue(object : Callback<Game1Model?> {
            override fun onResponse(call: Call<Game1Model?>, response: Response<Game1Model?>) {

                if (response.isSuccessful) {

                    val game1Model = response.body()
                    stringList = game1Model?.examples as MutableList<String>?

                    if (stringList != null && stringList!!.isNotEmpty()) {

                        for (i in 0 until stringList!!.size) {
                            lastValues = stringList!![i].split(" ").map { it.trim() }

                            if (lastValues.size <= 8) {
                                correctAnswers.add(stringList!![i])
                                break;
                            } else {
                                firstSentence()
                            }
                        }

//                        val lastValues = stringList!![0].split(" ").map { it.trim() }
                        notShuffleArrayList.addAll(lastValues)
                        shuffleArrayList.addAll(lastValues)

                        shuffleArrayList.shuffle()

                        //                                    Log.d(myTag, "shuffle array list is : $shuffleArrayList")
                        Log.d(myTag, "notshuffle array list is : $notShuffleArrayList")

                        adapter1 = CustomAdapter(
                            shuffleArrayList2,
                            this@Game1Activity,
                            this@Game1Activity,
                            0
                        )
                        adapter2 = CustomAdapter(
                            shuffleArrayList,
                            this@Game1Activity,
                            this@Game1Activity,
                            1
                        )

                        //                      val linearLayoutManager =LinearLayoutManager(this@Game1Activity,LinearLayoutManager.HORIZONTAL,false)
                        val linearLayoutManager2 = LinearLayoutManager(
                            this@Game1Activity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        val gridLayoutManager = GridLayoutManager(
                            this@Game1Activity,
                            3,
                            GridLayoutManager.VERTICAL,
                            false
                        )
                        val staggeredGridLayoutManager =
                            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

                        binding.apply {

                            recyclerView1.layoutManager = gridLayoutManager
                            recyclerView1.layoutManager = staggeredGridLayoutManager

                            recyclerView1.adapter = adapter1
                            recyclerView1.adapter = adapter2

                            recyclerView1.setOnDragListener(adapter1.dragInstance)
                            recyclerView1.setOnDragListener(adapter2.dragInstance)
                        }
                        
                    } else if (stringList!!.isEmpty()) {
                        firstSentence()
                    }

                } else {
                    Log.d(myTag, "error is :" + response.code())
                }
            }

            override fun onFailure(call: Call<Game1Model?>, t: Throwable) {
                Log.d(myTag, "failure is :" + t.localizedMessage)
            }
        })

    }


    fun secondSentence(lastValues: List<String>) {

        notShuffleArrayList.clear()
        shuffleArrayList.clear()
        shuffleArrayList2.clear()

        notShuffleArrayList.addAll(lastValues)
        shuffleArrayList.addAll(lastValues)
        binding.hintTextView.text = "Word at 3rd position is " + notShuffleArrayList[2]

        shuffleArrayList.shuffle()

        Log.d(myTag, "notshuffle array list is : $notShuffleArrayList")

        adapter1 = CustomAdapter(shuffleArrayList2, this@Game1Activity, this@Game1Activity, 0)
        adapter2 = CustomAdapter(shuffleArrayList,this@Game1Activity, this@Game1Activity, 1)

//                      val linearLayoutManager =LinearLayoutManager(thisthis@Game1Activity,LinearLayoutManager.HORIZONTAL,false)
        val linearLayoutManager2 =
            LinearLayoutManager(this@Game1Activity, LinearLayoutManager.VERTICAL, false)
        val gridLayoutManager =
            GridLayoutManager(this@Game1Activity, 3, GridLayoutManager.VERTICAL, false)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        binding.apply {

            recyclerView1.layoutManager = gridLayoutManager
            recyclerView1.layoutManager = staggeredGridLayoutManager

            recyclerView1.adapter = adapter1
            recyclerView1.adapter = adapter2

            recyclerView1.setOnDragListener(adapter1.dragInstance)
            recyclerView1.setOnDragListener(adapter2.dragInstance)
        }

    }

    override fun setVisibility(visibility: Int, guideTextView: Int) {
        findViewById<TextView>(guideTextView).visibility = View.INVISIBLE
    }

    override fun setVisibilityAnimSwipe(visibility: Int, lottieAnime: Int) {
    }

    override fun setVisibilityAnim(visibility: Int, lottieAnimeSuccess: Int) {}

    override fun changeText(View: Int) {}

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

//    private fun haveNetworkConnection(): Boolean {
//        var haveConnectedWifi = false
//        var haveConnectedMobile = false
//        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val netInfo = cm.allNetworkInfo
//        for (ni in netInfo) {
//            if (ni.typeName.equals(
//                    "WIFI",
//                    ignoreCase = true
//                )
//            ) if (ni.isConnected) haveConnectedWifi = true
//            if (ni.typeName.equals(
//                    "MOBILE",
//                    ignoreCase = true
//                )
//            ) if (ni.isConnected) haveConnectedMobile = true
//        }
//        return haveConnectedWifi || haveConnectedMobile
//    }


    override fun onDestroy() {
        super.onDestroy()
//        adapter1.clearItems()
//        adapter2.clearItems()
        binding.recyclerView1.adapter = null
    }

    override fun onPause() {
        super.onPause()
        game.pause()
//        adapter1.clearItems()
//        adapter2.clearItems()
    }

    override fun onStop() {
        super.onStop()
        shuffleArrayList.clear()
        shuffleArrayList2.clear()
    }

    override fun onResume() {
        super.onResume()
        if (isNetworkAvailable()) {
            if (DashboardActivity.volumeMain) {
                game.isLooping = true
                game.start()
            }
        } else {
            game.start()
        }
    }


    override fun onBackPressed() {

        if (binding.hintTextView.visibility == View.VISIBLE) {
            binding.hintTextView.visibility = View.INVISIBLE
        } else {
            super.onBackPressed()
        }

    }
}

