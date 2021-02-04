package com.android.developer.expert.presentation.detail

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(@DimenRes private val startEnd: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.top = view.marginTop
        outRect.bottom = view.marginBottom

        when (parent.getChildAdapterPosition(view)) {
            0 -> {
                outRect.left = startEnd
                outRect.right = view.marginRight
            }
            parent.adapter?.itemCount?.minus(1) -> {
                outRect.left = view.marginLeft
                outRect.right = startEnd
            }
            else -> {
                outRect.left = view.marginLeft
                outRect.right = view.marginRight
            }
        }
    }
}