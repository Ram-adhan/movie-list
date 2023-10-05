package com.inbedroom.edottest.shared.utils

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val horizontalSpacing: Int = 0,
    private val verticalSpacing: Int = 0,
    private val includeEdge: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val pos = parent.getChildAdapterPosition(view)
        if (!includeEdge && (pos == 0)) {
            outRect.left = 0
        } else {
            outRect.left = horizontalSpacing
        }
        if (!includeEdge && (pos == (state.itemCount - 1))) {
            outRect.right = 0
        } else {
            outRect.right = horizontalSpacing
        }
    }
}