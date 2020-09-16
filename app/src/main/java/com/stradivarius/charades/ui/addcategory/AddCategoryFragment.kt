package com.stradivarius.charades.ui.addcategory

import android.os.Bundle
import android.view.*
import com.stradivarius.charades.R
import com.stradivarius.charades.data.model.AddCategoryModel
import com.stradivarius.charades.databinding.AddCategoryBinding
import com.stradivarius.charades.ui.common.BaseViewModelFragment
import com.stradivarius.charades.ui.common.State

class AddCategoryFragment
    : BaseViewModelFragment<AddCategoryViewModel, AddCategoryModel, AddCategoryBinding>(
    AddCategoryViewModel::class.java,
    R.layout.add_category
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun bindViewModel(viewModel: AddCategoryViewModel, boundLayout: AddCategoryBinding) {
        boundLayout.model = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_category, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.check_mark -> viewModel.submitForm()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStateChanged(state: State<AddCategoryModel>) {
        super.onStateChanged(state)
        when (state) {
            is State.Error -> {
                when (state.errorType) {
                    is State.ErrorTypes.Warning -> {
                        showShortToast(R.string.form_submit_error)
                    }
                    is State.ErrorTypes.Critical -> {
                        showShortToast(R.string.add_category_fatal_error)
                    }
                }
            }
            is State.Success -> {
                activity?.finish()
            }
        }
    }
}