package com.shivam.learningapp.adapters

import android.content.Context
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shivam.learningapp.game1.Game1Activity
import com.shivam.learningapp.R
import com.shivam.learningapp.interfaces.CustomListener

class DragListener internal constructor(val context: Context,private val listener: CustomListener) : View.OnDragListener {

    companion object{
        var correct :Boolean =false
    }

    private var isDropped = false
    override
    fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DROP -> {

                isDropped = true

                var positionTarget = -1
                val viewSource = event.localState as View?
                val viewId = v.id
                val frameLayoutItem = R.id.frame_layout_item
                val emptyTextView1 = R.id.empty_list_text_view_1
                val emptyTextView2 = R.id.empty_list_text_view_2
                val recyclerView1 = R.id.recycler_view_1
                val guideTextView =R.id.guideTextView
                val recyclerView2 = R.id.recycler_view_2
                when (viewId) {
                    frameLayoutItem, emptyTextView1, emptyTextView2, recyclerView1, recyclerView2 -> {

                        val target: RecyclerView
                        when (viewId) {
                            emptyTextView1, recyclerView1 -> target = v.rootView.findViewById<View>(recyclerView1) as RecyclerView
                            emptyTextView2, recyclerView2 -> target = v.rootView.findViewById<View>(recyclerView2) as RecyclerView
                            else -> {
                                target = v.parent as RecyclerView
                                positionTarget = v.tag as Int
                            }
                        }
                        if (viewSource != null) {

                            val source = viewSource.parent as RecyclerView
                            val adapterSource = source.adapter as CustomAdapter?
                            val positionSource = viewSource.tag as Int
                            val list: String? = adapterSource?.getList()?.get(positionSource)

                            val listSource = adapterSource?.getList()?.apply {
                                removeAt(positionSource)
                            }

                            listSource?.let { adapterSource.updateList(it) }
                            adapterSource?.notifyDataSetChanged()


                            val adapterTarget = target.adapter as CustomAdapter?
                            val customListTarget = adapterTarget?.getList()

                            if (positionTarget >= 0) {
                                list?.let { customListTarget?.add(positionTarget, it) }
                                listener.setVisibility(View.INVISIBLE,guideTextView)
//                                Log.d("MyTag", "added Position>=0 : $positionTarget , $list")


                            } else {
                                list?.let { customListTarget?.add(it) }
                                listener.setVisibility(View.INVISIBLE,guideTextView)
                            }

                            customListTarget?.let { adapterTarget.updateList(it) }
                            adapterTarget?.notifyDataSetChanged()

                            if (source.id==recyclerView1 || target.id==recyclerView1){
//                                Log.d("MyTag", "custom List Target: $customListTarget")

                                if (customListTarget== Game1Activity.notShuffleArrayList){
                                    correct=true
//                                    Toast.makeText(context,"Congratulation",Toast.LENGTH_SHORT).show()
//                                    Toasty.success(context,"Congratulation",Toasty.LENGTH_SHORT,false).show()
                                    Game1Activity.checkSuccess=true
//                                    listener.setVisibility(View.VISIBLE,lottieAnimeSuccess)
                                }else{
                                    correct= false
                                }
                            }

//                            if (source.id == recyclerView2 && adapterSource?.itemCount ?: 0 < 1) {
//                                listener.setEmptyList(View.VISIBLE, recyclerView2, emptyTextView2)
//                            }
//                            if (viewId == emptyTextView2) {
//                                listener.setEmptyList(View.GONE, recyclerView2, emptyTextView2)
//                            }
//                            if (source.id == recyclerView1 && adapterSource?.itemCount ?: 0 < 1) {
//                                listener.setEmptyList(View.VISIBLE, recyclerView1, emptyTextView1)
//                            }
//                            if (viewId == emptyTextView1) {
//                                listener.setEmptyList(View.GONE, recyclerView1, emptyTextView1)
//                            }
                        }
                    }
                }
            }
        }
        if (!isDropped && event.localState != null) {
            (event.localState as View).visibility = View.VISIBLE
        }
        return true
    }
}