package com.project.nit3213.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.nit3213.data.model.DashboardResponse
import com.project.nit3213.data.model.EntityItem
import com.project.nit3213.data.repository.DashboardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

// Unit tests for DashboardViewModel using JUnit4, Mockito, and Coroutines Test Dispatcher
@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    // Swaps background executor used by Architecture Components with synchronous one
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: DashboardRepository
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Replace Main dispatcher with test dispatcher
        repository = mock()                  // Create mock DashboardRepository instance
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset Main dispatcher after test execution
    }

    // Tests that a successful dashboard API call emits DashboardState.Success with entity list
    @Test
    fun fetchDashboard_success_emitsEntities() = runTest {
        val entities = listOf(EntityItem("Prop1", "Prop2", "Desc"))
        val mockResponse = Response.success(DashboardResponse(entities, 1))
        whenever(repository.getDashboard(any())).thenReturn(mockResponse)

        viewModel.fetchDashboard("topicName")
        testDispatcher.scheduler.advanceUntilIdle() // Execute pending coroutines

        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardState.Success)
        assertEquals(1, (state as DashboardState.Success).entities.size)
        assertEquals("Prop1", state.entities[0].property1)
    }
}

