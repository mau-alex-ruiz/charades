package com.stradivarius.charades.ui.category.edit

import android.content.Context
import android.content.Intent
import com.stradivarius.charades.ui.common.BaseFragmentActivity

class EditCategoryActivity : BaseFragmentActivity(EditCategoryFragment::class.java) {

    companion object {

        fun createIntent(
            context: Context,
            categoryTitle: String,
            categoryList: List<String>
        ): Intent = Intent(context, EditCategoryActivity::class.java).apply {
            putExtra(KEY_BUNDLE, EditCategoryFragment.newBundle(categoryTitle, categoryList))
        }

        fun startActivity(
            context: Context,
            categoryTitle: String,
            categoryList: List<String>
        ) {
            context.startActivity(createIntent(context, categoryTitle, categoryList))
        }

    }

}