package com.stradivarius.charades.ui.utils.itemtouchhelper

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperAdapter {

    fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

}