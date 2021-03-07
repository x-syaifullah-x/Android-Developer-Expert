package com.android.developer.expert.di.component

import androidx.viewbinding.ViewBinding
import com.android.developer.expert.base.BaseActivity
import id.xxx.the.movie.db.di.dagger.TheMovieDbComponent
import com.android.developer.expert.di.module.ViewModelModule
import dagger.Component
import id.xxx.base.di.annotation.AppScope

@AppScope
@Component(
    dependencies = [TheMovieDbComponent::class],
    modules = [ViewModelModule::class]
)

interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(dataComponent: TheMovieDbComponent): AppComponent
    }

    fun inject(activity: BaseActivity<ViewBinding>)
}