package com.android.developer.expert.core.data.di.module

import androidx.paging.ExperimentalPagingApi
import com.android.developer.expert.core.data.repository.FavoriteRepositoryImpl
import com.android.developer.expert.core.data.repository.MovieRepositoryImpl
import com.android.developer.expert.core.data.repository.SearchRepositoryImpl
import com.android.developer.expert.core.data.repository.TvRepositoryImpl
import com.android.developer.expert.core.domain.repository.FavoriteRepository
import com.android.developer.expert.core.domain.repository.MovieRepository
import com.android.developer.expert.core.domain.repository.SearchRepository
import com.android.developer.expert.core.domain.repository.TvRepository
import com.android.developer.expert.core.domain.usecase.Interactor
import com.android.developer.expert.core.domain.usecase.InteractorImpl
import com.the.movie.db.di.module.DatabaseModule
import com.the.movie.db.di.module.NetworkModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class
    ]
)
@Suppress("unused")
abstract class RepositoryModule {

    @Binds
    @Singleton
    @ExperimentalPagingApi
    abstract fun bindsTvRepositoryImpl(@Singleton repository: TvRepositoryImpl): TvRepository

    @Binds
    @Singleton
    @ExperimentalPagingApi
    abstract fun bindsMovieRepositoryImpl(@Singleton repository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindsSearchRepositoryImpl(@Singleton repository: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindsFavoriteRepositoryImpl(@Singleton repository: FavoriteRepositoryImpl): FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindsInteractImpl(@Singleton interact: InteractorImpl): Interactor
}