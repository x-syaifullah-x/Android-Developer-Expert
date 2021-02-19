package com.android.developer.expert.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.android.developer.expert.App
import com.android.developer.expert.di.multibinding.ViewModelFactory
import com.base.binding.activity.BaseActivityWithNavigation
import javax.inject.Inject

abstract class BaseActivity<ActivityViewBinding : ViewBinding> :
    BaseActivityWithNavigation<ActivityViewBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    open fun isInject(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        @Suppress("UNCHECKED_CAST")
        if (isInject()) (application as App).appComponent.inject(this as BaseActivity<ViewBinding>)
        super.onCreate(savedInstanceState)
    }
}