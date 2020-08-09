package com.stradivarius.charades.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stradivarius.charades.databinding.ItemLayoutBinding

class ItemFragment(private val item: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ItemLayoutBinding.inflate(inflater, container, false).apply {
            item.text = this@ItemFragment.item
        }
        return binding.root
    }

}