package com.android.developer.expert

import com.android.developer.expert.di.component.AppComponent
import com.android.developer.expert.di.component.DaggerAppComponent
import com.google.android.play.core.splitcompat.SplitCompatApplication
import id.xxx.the.movie.db.di.dagger.DaggerTheMovieDbComponent
import id.xxx.the.movie.db.di.dagger.TheMovieDbComponent
import timber.log.Timber

abstract class App : SplitCompatApplication() {

    val dataComponent: TheMovieDbComponent by lazy {
        DaggerTheMovieDbComponent.factory().create(this)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(dataComponent)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}