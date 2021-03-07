package com.android.developer.expert.presentation.favorite.di.module

import androidx.lifecycle.ViewModel
import com.android.developer.expert.presentation.favorite.FavoriteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.xxx.base.di.annotation.ViewModelKey
import id.xxx.base.di.module.BaseViewModelModule

@Module
@Suppress("unused")
abstract class ViewModelModule : BaseViewModelModule() {
    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    protected abstract fun bindsFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel
}