package com.stradivarius.charades.ui.results

import com.stradivarius.charades.R
import com.stradivarius.charades.data.common.Model
import com.stradivarius.charades.databinding.ResultsLayoutBinding
import com.stradivarius.charades.ui.common.BaseViewModelFragment

class ResultsFragment : BaseViewModelFragment<ResultsViewModel, Model, ResultsLayoutBinding>(
    ResultsViewModel::class.java,
    R.layout.results_layout
) {

    override fun bindViewModel(viewModel: ResultsViewModel, boundLayout: ResultsLayoutBinding) {

    }

}