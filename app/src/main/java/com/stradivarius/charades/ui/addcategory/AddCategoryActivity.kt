package com.stradivarius.charades.ui.addcategory

import android.content.Context
import android.content.Intent
import com.stradivarius.charades.ui.common.BaseFragmentActivity

class AddCategoryActivity : BaseFragmentActivity(AddCategoryFragment::class.java) {

    companion object {

        private fun createIntent(
            context: Context
        ): Intent = Intent(context, AddCategoryActivity::class.java)

        fun startActivity(
            context: Context
        ) = context.startActivity(createIntent(context))

    }

}