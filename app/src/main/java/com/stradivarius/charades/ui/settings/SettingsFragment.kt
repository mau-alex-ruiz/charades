package com.stradivarius.charades.ui.settings

import com.stradivarius.charades.R
import com.stradivarius.charades.data.model.SettingsModel
import com.stradivarius.charades.databinding.SettingsBinding
import com.stradivarius.charades.ui.common.BaseViewModelFragment

class SettingsFragment : BaseViewModelFragment<SettingsViewModel, SettingsModel, SettingsBinding>(
    SettingsViewModel::class.java,
    R.layout.settings
) {

    override fun bindViewModel(viewModel: SettingsViewModel, boundLayout: SettingsBinding) {
        boundLayout.model = viewModel
    }

}