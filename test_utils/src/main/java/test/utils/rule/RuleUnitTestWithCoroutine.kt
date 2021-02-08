package test.utils.rule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

abstract class RuleUnitTestWithCoroutine {

    @ExperimentalCoroutinesApi
    class MainCoroutineScopeRule @ExperimentalCoroutinesApi constructor(
        private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    ) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(dispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            cleanupTestCoroutines()
            Dispatchers.resetMain()
        }
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @Before
    open fun before() = Unit

    @After
    open fun after() = Unit
}