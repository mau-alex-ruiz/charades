package com.stradivarius.charades.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stradivarius.charades.R
import com.stradivarius.charades.ui.common.core.FragmentFactory

abstract class BaseFragmentActivity(
    private val fragmentClass: Class<out BaseViewModelFragment<*,*,*>>
) : AppCompatActivity() {

    companion object {
        const val KEY_BUNDLE = "bundle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = FragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    supportFragmentManager.fragmentFactory.instantiate(
                        classLoader,
                        fragmentClass.canonicalName!!
                    ).apply {
                        arguments = intent?.extras?.getBundle(KEY_BUNDLE)
                    }
                )
                .commitNow()
        }
    }

}