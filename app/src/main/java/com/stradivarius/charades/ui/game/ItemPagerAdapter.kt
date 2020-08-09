package com.stradivarius.charades.ui.game

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ItemPagerAdapter(
    fm: FragmentManager,
    private val itemList: Array<CharSequence>
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return ItemFragment(itemList[position].toString())
    }

    override fun getCount(): Int = itemList.size
}