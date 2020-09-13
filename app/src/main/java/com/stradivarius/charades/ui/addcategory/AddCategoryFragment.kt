package com.stradivarius.charades.ui.addcategory

import com.stradivarius.charades.R
import com.stradivarius.charades.data.model.AddCategory
import com.stradivarius.charades.databinding.AddCategoryBinding
import com.stradivarius.charades.ui.common.BaseViewModelFragment

class AddCategoryFragment
    : BaseViewModelFragment<AddCategoryViewModel, AddCategory, AddCategoryBinding>(
    AddCategoryViewModel::class.java,
    R.layout.add_category
) {

    override fun bindViewModel(viewModel: AddCategoryViewModel, boundLayout: AddCategoryBinding) {
        boundLayout.model = viewModel
    }

}