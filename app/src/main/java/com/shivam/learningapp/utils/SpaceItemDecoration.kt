package com.shivam.learningapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class SpaceItemDecoration(private val space: Int) : ItemDecoration() {
    override
    fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        // Add top margin only for the first item to avoid double space between items
        if (view.let { parent.getChildLayoutPosition(it) } == 0) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}