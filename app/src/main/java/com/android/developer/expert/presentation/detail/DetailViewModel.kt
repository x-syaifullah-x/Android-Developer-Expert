package com.android.developer.expert.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.xxx.the.movie.db.domain.model.Type
import id.xxx.the.movie.db.domain.usecase.Interactor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val interact: Interactor) : ViewModel() {

    private val coroutineContext = viewModelScope.coroutineContext

    private val sharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(1)

    val movie by lazy {
        sharedFlow.asSharedFlow().flatMapLatest {
            interact.getMovie(it).distinctUntilChanged().stateIn(viewModelScope)
        }.asLiveData(coroutineContext)
    }

    val tv by lazy {
        sharedFlow.asSharedFlow().flatMapLatest {
            interact.getTv(it).distinctUntilChanged().stateIn(viewModelScope)
        }.asLiveData(coroutineContext)
    }

    fun refresh(id: Int) =
        viewModelScope.launch { sharedFlow.emit(id) }

    fun setFavorite(type: Type<*>, isFavorite: Boolean) =
        viewModelScope.launch { interact.setFavorite(type, isFavorite) }
}