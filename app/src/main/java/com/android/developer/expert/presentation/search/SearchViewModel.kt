package com.android.developer.expert.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.android.developer.expert.core.domain.usecase.Interactor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val interact: Interactor) : ViewModel() {

    companion object {
        const val QUERY_TIME_OUT = 300L
    }

    private val statFlow: MutableStateFlow<String> = MutableStateFlow("")

    val searchResult = statFlow
        .debounce(QUERY_TIME_OUT)
        .flatMapLatest { interact.search(it).stateIn(viewModelScope) }
        .asLiveData(viewModelScope.coroutineContext)

    fun send(query: String) =
        viewModelScope.launch { statFlow.emit(query) }
}