package com.shivam.learningapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shivam.learningapp.R
import java.util.*

class ResultAdapter(private var list: ArrayList<String>, private val context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder?>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.result_recyclerview_item, parent, false)
        return ListItem1ViewHolder(view)

    }

    override fun getItemCount(): Int{
//        Log.d("MyTag","Get Item Count : "+list.size.toString())
        return list.size
    }

    fun clearItems() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder as ListItem1ViewHolder
        holder.textView2.text = list[position]
        holder.textView.text=(position+1).toString()

    }

    inner class ListItem1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textView: TextView = itemView.findViewById(R.id.text_number)
        var textView2: TextView = itemView.findViewById(R.id.text_answer)
    }


}
