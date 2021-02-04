package com.android.developer.expert.core.di.component

import android.app.Application
import com.android.developer.expert.core.di.module.RepositoryModule
import com.android.developer.expert.core.domain.usecase.Interactor
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class
    ]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): CoreComponent
    }

    @Suppress("SpellCheckingInspection")
    fun provideInteractor(): Interactor
}