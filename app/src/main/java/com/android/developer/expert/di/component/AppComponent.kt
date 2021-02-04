package com.android.developer.expert.di.component

import androidx.viewbinding.ViewBinding
import com.android.developer.expert.base.BaseActivity
import com.android.developer.expert.core.data.di.component.CoreComponent
import com.android.developer.expert.di.annotation.AppScope
import com.android.developer.expert.di.module.ViewModelModule
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [ViewModelModule::class]
)

interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(dataComponent: CoreComponent): AppComponent
    }

    fun inject(activity: BaseActivity<ViewBinding>)
}