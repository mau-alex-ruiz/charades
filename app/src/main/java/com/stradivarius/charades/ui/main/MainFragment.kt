package com.stradivarius.charades.ui.main

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.stradivarius.charades.R
import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.databinding.MainFragmentBinding
import com.stradivarius.charades.ui.common.BaseViewModelFragment
import com.stradivarius.charades.ui.common.State
import com.stradivarius.charades.ui.utils.CardTouchHelperAdapter

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
                boundLayout.categoriesRecyclerView.apply {
                    MainAdapter(state.model!!.categories).also {
                        adapter = it
                        ItemTouchHelper(CardTouchHelperAdapter(it)).attachToRecyclerView(this)
                    }
                }
            }
        }
    }
}
