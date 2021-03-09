package com.android.developer.expert.presentation.favorite

import androidx.lifecycle.Observer
import androidx.paging.PagingData
import id.xxx.test.rule.RuleUnitTestWithCoroutine
import id.xxx.the.movie.db.domain.model.ItemModel
import id.xxx.the.movie.db.domain.usecase.Interactor
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest : RuleUnitTestWithCoroutine() {
    private lateinit var viewModel: FavoriteViewModel

    private lateinit var data: PagingData<ItemModel>

    @Mock
    lateinit var interact: Interactor

    @Mock
    lateinit var mockObserver: Observer<PagingData<ItemModel>>

    @Captor
    lateinit var captor: ArgumentCaptor<PagingData<ItemModel>>

    override fun before() {
        viewModel = FavoriteViewModel(interact)
    }

    @Test
    fun discoverMovieEmptyTest() {
        data = PagingData.empty()

        val flow = flow { emit(data) }

        Mockito.`when`(interact.getMovieFavorite()).thenReturn(flow)

        viewModel.movieFav.observeForever(mockObserver)

        Mockito.verify(interact).getMovieFavorite()

        Mockito.verify(mockObserver).onChanged(captor.capture())

        Assert.assertNotNull(captor.value)
    }

    @Test
    fun discoverTvEmptyTest() {
        data = PagingData.empty()

        val flow = flow { emit(data) }

        Mockito.`when`(interact.getTvFavorite()).thenReturn(flow)

        viewModel.tvFav.observeForever(mockObserver)

        Mockito.verify(interact).getTvFavorite()

        Mockito.verify(mockObserver).onChanged(captor.capture())

        Assert.assertNotNull(captor.value)
    }

    @Test
    fun statScroll() {
        viewModel.statScroll["test"] = 100
        Assert.assertEquals(100, viewModel.statScroll["test"])
    }
}