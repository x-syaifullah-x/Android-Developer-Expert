package com.android.developer.expert.presentation

import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.android.developer.expert.core.domain.model.ItemModel
import com.android.developer.expert.core.domain.usecase.Interactor
import com.android.developer.expert.utils.RuleUnitTestWithMockito
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito

class MainViewModelTest : RuleUnitTestWithMockito() {
    private lateinit var viewModel: MainViewModel

    private lateinit var data: PagingData<ItemModel>

    @Mock
    lateinit var interact: Interactor

    @Mock
    lateinit var mockObserver: Observer<PagingData<ItemModel>>

    @Captor
    lateinit var captor: ArgumentCaptor<PagingData<ItemModel>>

    override fun before() {
        viewModel = MainViewModel(interact)
    }

    @Test
    fun discoverMovieEmptyTest() {
        data = PagingData.empty()

        val flow = flow { emit(data) }

        Mockito.`when`(interact.getMovieDiscover()).thenReturn(flow)

        viewModel.discoverMovie.observeForever(mockObserver)

        Mockito.verify(interact).getMovieDiscover()

        Mockito.verify(mockObserver).onChanged(captor.capture())

        Assert.assertNotNull(captor.value)
    }

    @Test
    fun discoverTvEmptyTest() {
        data = PagingData.empty()

        val flow = flow { emit(data) }

        Mockito.`when`(interact.getDiscoverTv()).thenReturn(flow)

        viewModel.discoverTv.observeForever(mockObserver)

        Mockito.verify(interact).getDiscoverTv()

        Mockito.verify(mockObserver).onChanged(captor.capture())

        Assert.assertNotNull(captor.value)
    }

    @Test
    fun statScroll() {
        viewModel.statScroll["test"] = 100
        Assert.assertEquals(100, viewModel.statScroll["test"])
    }
}