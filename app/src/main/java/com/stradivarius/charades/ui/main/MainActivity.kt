package com.stradivarius.charades.ui.main

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.stradivarius.charades.R
import com.stradivarius.charades.data.common.Model
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

}