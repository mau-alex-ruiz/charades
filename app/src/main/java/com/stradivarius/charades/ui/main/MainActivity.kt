package com.stradivarius.charades.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.ViewDataBinding
import com.stradivarius.charades.R
import com.stradivarius.charades.data.common.Model
import com.stradivarius.charades.ui.addcategory.AddCategoryActivity
import com.stradivarius.charades.ui.addcategory.AddCategoryFragment
import com.stradivarius.charades.ui.common.BaseFragmentActivity
import com.stradivarius.charades.ui.common.BaseViewModel
import com.stradivarius.charades.ui.common.BaseViewModelFragment
import com.stradivarius.charades.ui.common.core.Di

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
            R.id.add_list -> AddCategoryActivity.startActivity(this)
        }
        return super.onOptionsItemSelected(item)
    }

}