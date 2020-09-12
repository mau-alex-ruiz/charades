package com.stradivarius.charades.ui.common.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.stradivarius.charades.ui.game.container.ItemContainerFragment
import com.stradivarius.charades.ui.main.MainFragment

class FragmentFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(loadFragmentClass(classLoader, className)) {
            MainFragment::class.java ->
                MainFragment()
            ItemContainerFragment::class.java ->
                ItemContainerFragment()
            else -> super.instantiate(classLoader, className)
        }
    }

}