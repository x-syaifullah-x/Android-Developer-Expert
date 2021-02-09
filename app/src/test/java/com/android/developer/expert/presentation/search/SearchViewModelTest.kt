package com.android.developer.expert.presentation.search

import androidx.lifecycle.Observer
import com.android.developer.expert.core.domain.model.Resource
import com.android.developer.expert.core.domain.model.SearchModel
import com.android.developer.expert.core.domain.usecase.Interactor
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import test.utils.rule.RuleUnitTestWithCoroutine

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest : RuleUnitTestWithCoroutine() {

    private lateinit var viewModel: SearchViewModel

    @Mock
    lateinit var interact: Interactor

    @Mock
    lateinit var mockObserver: Observer<Resource<List<SearchModel>>>

    @Captor
    lateinit var captor: ArgumentCaptor<Resource<List<SearchModel>>>

    override fun before() {
        viewModel = SearchViewModel(interact)
    }

    @Test
    fun searchTest() {
        val query = "test"
        val errorMessage = "test_error"
        Mockito.`when`(interact.search(query))
            .thenReturn(flowOf(Resource.Error(Exception(errorMessage))))
        viewModel.send(query)
        viewModel.searchResult.observeForever(mockObserver)
        mainCoroutineScopeRule.advanceTimeBy(SearchViewModel.QUERY_TIME_OUT)

        Mockito.verify(interact).search(query)
        Mockito.verify(mockObserver).onChanged(captor.capture())

        Assert.assertTrue(captor.value is Resource.Error)
    }
}