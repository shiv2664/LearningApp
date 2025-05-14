package com.shivam.learningapp.adapters


import android.content.ClipData
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.shivam.learningapp.R
import com.shivam.learningapp.interfaces.CustomListener


class CustomAdapter(private var list: ArrayList<String>, private val listener: CustomListener?
                    , private val context: Context, val itemType:Int)
    : RecyclerView.Adapter<RecyclerView.ViewHolder?>(), View.OnTouchListener,View.OnClickListener {

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (itemType == 0) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.list_item_sentences, parent, false)
            ListItem1ViewHolder(view)
        }
        else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.list_item_sentences, parent, false)
            ListItem2ViewHolder(view)
        }

//        val inflater = LayoutInflater.from(parent.context)
//        return CustomViewHolder(inflater, parent)

    }

    override fun getItemCount(): Int{
//        Log.d("MyTag","Get Item Count : "+list.size.toString())
        return list.size
    }

    fun clearItems() {
        list.clear()
        notifyDataSetChanged()
    }

    fun updateList(list: MutableList<String>) {
        this.list = list as ArrayList<String>
    }

    fun getList(): MutableList<String> = this.list.toMutableList()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(v)
                v?.startDragAndDrop(data, shadowBuilder, v, 0)
                return true
            }
        }
        return false
    }



    val dragInstance: DragListener?
        get() = if (listener != null) {
            DragListener(context,listener)
        } else {
            Log.e(javaClass::class.simpleName, "Listener not initialized")
            null
        }

//    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
//        holder.text.text = list[position]
//        holder.frameLayout.tag = position
//        holder.frameLayout?.setOnTouchListener(this)
//        holder.frameLayout.setOnDragListener(DragListener(context))
//    }

    class CustomViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
        var text: TextView = itemView.findViewById(R.id.text)
        var frameLayout: FrameLayout = itemView.findViewById(R.id.frame_layout_item)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        if (itemType == 0) {
            val listItem1 = holder as ListItem1ViewHolder
            holder.text.text = list[position]
            holder.frameLayout.tag = position
            holder.frameLayout?.setOnTouchListener(this)
            holder.frameLayout.setOnClickListener(this)
            holder.frameLayout.setOnDragListener(listener?.let { DragListener(context, it) })

        }else
        {
            val listItem1 = holder as ListItem2ViewHolder
            holder.text.text = list[position]
            holder.frameLayout.tag = position
            holder.frameLayout?.setOnTouchListener(this)
            holder.frameLayout.setOnClickListener(this)
            holder.frameLayout.setOnDragListener(listener?.let { DragListener(context, it) })
        }


    }

    inner class ListItem1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var text: TextView = itemView.findViewById(R.id.text)
        var frameLayout: FrameLayout = itemView.findViewById(R.id.frame_layout_item)
    }

    inner class ListItem2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var text: TextView = itemView.findViewById(R.id.text)
        var frameLayout: FrameLayout = itemView.findViewById(R.id.frame_layout_item)
    }

    override fun onClick(v: View?) {
    }

}
