package com.stradivarius.charades.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.stradivarius.charades.R
import com.stradivarius.charades.ui.category.add.AddCategoryActivity
import com.stradivarius.charades.ui.common.BaseFragmentActivity
import com.stradivarius.charades.ui.common.core.Di
import com.stradivarius.charades.ui.settings.SettingsActivity

class MainActivity : BaseFragmentActivity(MainFragment::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Di.isInitialized) {
            Di.init(applicationContext!!.assets!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_cog -> SettingsActivity.startActivity(this)
            R.id.add_list -> AddCategoryActivity.startActivity(this)
        }
        return super.onOptionsItemSelected(item)
    }

}