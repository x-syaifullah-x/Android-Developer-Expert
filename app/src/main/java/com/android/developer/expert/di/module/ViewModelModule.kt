package com.android.developer.expert.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.developer.expert.di.annotation.ViewModelKey
import com.android.developer.expert.di.multibinding.ViewModelFactory
import com.android.developer.expert.presentation.MainViewModel
import com.android.developer.expert.presentation.detail.DetailViewModel
import com.android.developer.expert.presentation.search.SearchViewModel
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
    @ViewModelKey(MainViewModel::class)
    protected abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    protected abstract fun bindsDetailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    protected abstract fun bindsSearchViewModel(viewModel: SearchViewModel): ViewModel
}