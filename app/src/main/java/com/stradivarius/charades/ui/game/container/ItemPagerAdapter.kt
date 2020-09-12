package com.stradivarius.charades.ui.game.container

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.stradivarius.charades.ui.game.item.ItemFragment

class ItemPagerAdapter(
    fm: FragmentManager,
    private val itemList: Array<CharSequence>
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return ItemFragment(itemList[position].toString())
    }

    override fun getCount(): Int = itemList.size
}