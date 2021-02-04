package com.android.developer.expert.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.android.developer.expert.core.domain.usecase.Interactor
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interact: Interactor) : ViewModel() {

    private val coroutineContext = viewModelScope.coroutineContext

    val discoverMovie by lazy {
        interact.getMovieDiscover().cachedIn(viewModelScope).asLiveData(coroutineContext)
    }

    val discoverTv by lazy {
        interact.getDiscoverTv().cachedIn(viewModelScope).asLiveData(coroutineContext)
    }

    val statScroll: HashMap<String, Int> = hashMapOf()
}