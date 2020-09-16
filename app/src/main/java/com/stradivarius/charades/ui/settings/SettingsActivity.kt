package com.stradivarius.charades.ui.settings

import android.content.Context
import android.content.Intent
import com.stradivarius.charades.ui.common.BaseFragmentActivity

class SettingsActivity : BaseFragmentActivity(SettingsFragment::class.java) {

    companion object {

        private fun createIntent(
            context: Context
        ): Intent {
            return Intent(context, SettingsActivity::class.java)
        }

        fun startActivity(
            context: Context
        ) {
            context.startActivity(createIntent(context))
        }

    }

}