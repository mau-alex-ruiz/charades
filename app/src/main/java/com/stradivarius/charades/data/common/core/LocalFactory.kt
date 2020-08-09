package com.stradivarius.charades.data.common.core

import com.stradivarius.charades.data.common.Local

interface LocalFactory {

    fun <L : Local> create(clazz: Class<L>) : L

}