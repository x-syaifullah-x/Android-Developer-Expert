package com.android.developer.expert.presentation.favorite.di.component

import id.xxx.the.movie.db.di.dagger.TheMovieDbComponent
import com.android.developer.expert.presentation.favorite.FavoriteFragment
import com.android.developer.expert.presentation.favorite.di.module.ViewModelModule
import dagger.Component
import id.xxx.base.di.annotation.AppScope

@AppScope
@Component(
    dependencies = [TheMovieDbComponent::class],
    modules = [ViewModelModule::class]
)
interface FavoriteComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: TheMovieDbComponent): FavoriteComponent
    }

    fun inject(fragment: FavoriteFragment)
}