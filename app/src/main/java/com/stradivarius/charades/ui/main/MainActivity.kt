package com.stradivarius.charades.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stradivarius.charades.R
import com.stradivarius.charades.ui.common.core.Di

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    MainFragment.newInstance()
                )
                .commitNow()
        }
        if (!Di.isInitialized) {
            Di.init(applicationContext!!.assets!!)
        }
    }

}
