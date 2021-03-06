package com.android.developer.expert.presentation.favorite.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.developer.expert.di.annotation.ViewModelKey
import com.android.developer.expert.di.multibinding.ViewModelFactory
import com.android.developer.expert.presentation.favorite.FavoriteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("unused")
abstract class ViewModelModule {
    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    protected abstract fun bindsFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel
}