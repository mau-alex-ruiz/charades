package com.stradivarius.charades.ui.main

import com.stradivarius.charades.R
import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.databinding.MainFragmentBinding
import com.stradivarius.charades.ui.common.BaseViewModelFragment
import com.stradivarius.charades.ui.common.State

class MainFragment : BaseViewModelFragment<MainViewModel, MainModel, MainFragmentBinding>(
    layoutRes = R.layout.main_fragment,
    viewModelClass = MainViewModel::class.java
) {

    companion object {
        const val KEY_BUNDLE = "bundle"
    }

    override fun bindViewModel(viewModel: MainViewModel, boundLayout: MainFragmentBinding) {

        boundLayout.apply {
            model = viewModel.apply { init() }
        }
    }

    override fun onStateChanged(state: State<MainModel>) {
        when (state) {
            is State.Loaded -> {
                boundLayout.categoriesRecyclerView.adapter = MainAdapter(state.model!!.categories)
            }
        }
    }


}
