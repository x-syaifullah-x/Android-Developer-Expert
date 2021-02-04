package com.android.developer.expert.presentation.favorite.di.component

import com.android.developer.expert.core.di.component.CoreComponent
import com.android.developer.expert.di.annotation.AppScope
import com.android.developer.expert.presentation.favorite.FavoriteFragment
import com.android.developer.expert.presentation.favorite.di.module.ViewModelModule
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [ViewModelModule::class]
)
interface FavoriteComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): FavoriteComponent
    }

    fun inject(fragment: FavoriteFragment)
}