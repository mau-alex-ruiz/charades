package com.stradivarius.charades.data.common.core

import com.stradivarius.charades.data.common.Repository

interface RepositoryFactory {

    fun <R : Repository> create(clazz: Class<R>) : R

}