package com.stradivarius.charades.ui.category.edit

import android.os.Bundle
import com.stradivarius.charades.R
import com.stradivarius.charades.databinding.AddEditCategoryBinding
import com.stradivarius.charades.ui.category.base.BaseCategoryFragment

class EditCategoryFragment
    : BaseCategoryFragment<EditCategoryViewModel>(
    EditCategoryViewModel::class.java
) {

    companion object {
        private const val KEY_CATEGORY_TITLE = "categoryTitle"
        private const val KEY_CATEGORY_LIST = "categoryList"

        fun newBundle(categoryTitle: String,
                      categoryList: List<String>): Bundle {
            return Bundle().apply {
                putString(KEY_CATEGORY_TITLE, categoryTitle)
                putStringArrayList(KEY_CATEGORY_LIST, ArrayList(categoryList))
            }
        }

    }

    private var categoryTitle: String? = null
    private var categoryList: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            categoryTitle = savedInstanceState.getString(KEY_CATEGORY_TITLE)
            categoryList = savedInstanceState.getStringArrayList(KEY_CATEGORY_LIST)
        } else {
            categoryTitle = arguments?.getString(KEY_CATEGORY_TITLE)
            categoryList = arguments?.getStringArrayList(KEY_CATEGORY_LIST)
        }
        if (categoryTitle == null || categoryList == null) {
            showShortToast(R.string.edit_category_fatal_error)
            activity?.finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CATEGORY_TITLE, categoryTitle)
        outState.putStringArrayList(KEY_CATEGORY_LIST, categoryList)
    }

    override fun bindViewModel(
        viewModel: EditCategoryViewModel,
        boundLayout: AddEditCategoryBinding
    ) {
        boundLayout.model = viewModel.apply {
            init(categoryTitle!!, categoryList!!)
        }
    }

}