package com.stradivarius.charades.data.model

import com.stradivarius.charades.data.common.Model

class MainModel(
    val categories: List<Pair<String, List<String>>>
) : Model