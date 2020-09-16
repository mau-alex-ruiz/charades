package com.stradivarius.charades.ui.common.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.stradivarius.charades.ui.addcategory.AddCategoryFragment
import com.stradivarius.charades.ui.game.container.ItemContainerFragment
import com.stradivarius.charades.ui.main.MainFragment
import com.stradivarius.charades.ui.settings.SettingsFragment

class FragmentFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(loadFragmentClass(classLoader, className)) {
            MainFragment::class.java ->
                MainFragment()
            ItemContainerFragment::class.java ->
                ItemContainerFragment()
            AddCategoryFragment::class.java ->
                AddCategoryFragment()
            SettingsFragment::class.java ->
                SettingsFragment()
            else -> super.instantiate(classLoader, className)
        }
    }

}