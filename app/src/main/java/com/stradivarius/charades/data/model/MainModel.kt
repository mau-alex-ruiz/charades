package com.stradivarius.charades.data.model

import com.stradivarius.charades.data.common.Model
import com.stradivarius.charades.data.dto.MainDto

class MainModel private constructor(
    val categories: List<Pair<String, List<String>>>
) : Model {

    constructor(dto: MainDto) : this(
        dto.categoriesMap.toList()
    )

}