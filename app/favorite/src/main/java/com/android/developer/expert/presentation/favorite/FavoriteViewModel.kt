package com.android.developer.expert.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.android.developer.expert.domain.usecase.Interactor
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(interact: Interactor) : ViewModel() {

    private val coroutineContext = viewModelScope.coroutineContext

    val movieFav by lazy {
        interact.getMovieFavorite().cachedIn(viewModelScope).asLiveData(coroutineContext)
    }

    val tvFav by lazy {
        interact.getTvFavorite().cachedIn(viewModelScope).asLiveData(coroutineContext)
    }

    val statScroll: HashMap<String, Int> by lazy { hashMapOf() }
}