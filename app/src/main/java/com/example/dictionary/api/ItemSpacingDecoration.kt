package com.example.dictionary.api

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoration(private val context: Context, private val spacingDp: Int) : RecyclerView.ItemDecoration() {

    private val spacing: Int = (context.resources.displayMetrics.density * spacingDp).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, rv: RecyclerView, state: RecyclerView.State) {
        val itemPosition = rv.getChildAdapterPosition(view)
        if (itemPosition != rv.adapter?.itemCount?.minus(1)) {
            outRect.bottom = spacing
        }
    }
}