package com.android.developer.expert.base.adapter.paging

import androidx.annotation.CallSuper
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.android.developer.expert.domain.model.base.IModel
import timber.log.Timber

interface IStatPagingData<Model : IModel<Int>> {

    fun getAdapter(): BasePagingDataAdapter<*, Model>

    @CallSuper
    fun handleLoadStatListener(combinedLoadStates: CombinedLoadStates) {
        val isMae = combinedLoadStates.mediator?.append?.endOfPaginationReached ?: true
        val isEmpty = getAdapter().snapshot().isEmpty()
        val srIsNotLoading = combinedLoadStates.source.refresh is LoadState.NotLoading
        val mrIsError = combinedLoadStates.mediator?.refresh is LoadState.Error
        val rIsLoading = combinedLoadStates.refresh is LoadState.Loading
        val isSpe = combinedLoadStates.source.prepend.endOfPaginationReached
        val isSae = combinedLoadStates.source.append.endOfPaginationReached
        val rIsError = combinedLoadStates.refresh is LoadState.Error
        val onDataLoad =
            combinedLoadStates.refresh is LoadState.NotLoading && getAdapter().itemCount > 0

        if (onDataLoad) handleLoadListenerNotEmpty()

        if (srIsNotLoading && isSpe && isSae && isMae && isEmpty)
            handleLoadListenerDataEmpty()

        if (isMae && !isEmpty)
            handleLoadListenerEndPage()

        if (srIsNotLoading && mrIsError && isEmpty)
            handleLoadListenerFirstLoadErrorDataEmpty()

        if (rIsLoading)
            handleLoadListenerFirstLoading()

        if (rIsError && !isEmpty)
            handleLoadListenerFirstLoadErrorDataNotEmpty()
    }

    fun handleLoadListenerDataEmpty() =
        Timber.d("handleLoadStatListenerDataEmpty")

    fun handleLoadListenerEndPage() =
        Timber.d("handleLoadStatListenerEndPage")

    fun handleLoadListenerFirstLoadErrorDataEmpty() =
        Timber.d("handleLoadStatListenerFirstLoadErrorDataEmpty")

    fun handleLoadListenerFirstLoadErrorDataNotEmpty() {
        Timber.d("handleLoadStatListenerFirstLoadErrorDataNotEmpty")
    }

    fun handleLoadListenerFirstLoading() {
        Timber.d("handleLoadListenerFirstLoading")
    }

    fun handleLoadListenerNotEmpty() {
        Timber.d("handleLoadListenerNotEmpty")
    }
}