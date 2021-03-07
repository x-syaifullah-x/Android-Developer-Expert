package com.android.developer.expert.domain.model.base

import id.xxx.base.domain.model.BaseModel

interface IModel<T : Any> : BaseModel<T> {
    companion object {
        const val DATA_NOT_VALID = -0x0
    }

    override val id: T?
}