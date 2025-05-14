package com.shivam.learningapp.adapters

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shivam.learningapp.R
import java.util.*

class BodyPartAdapter(private var imageList: ArrayList<Int>, private var soundList:ArrayList<Int>
                      , private val context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder?>(){

//    var sound: MediaPlayer?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.recyclerview_body_item, parent, false)
        return ListItem1ViewHolder(view)

    }

    override fun getItemCount(): Int{
//        Log.d("MyTag","Get Item Count : "+list.size.toString())
        return imageList.size
    }

    fun clearItems() {
        imageList.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var sound: MediaPlayer?=null
        sound = MediaPlayer.create(context, soundList[position])
        holder as ListItem1ViewHolder
        holder.imageView.setBackgroundResource(imageList[position])

        holder.speaker.setOnClickListener {
            sound.start()
        }


    }

    inner class ListItem1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.roundedImageView)
        var speaker:ImageView=itemView.findViewById(R.id.speakerbody)
    }


}
