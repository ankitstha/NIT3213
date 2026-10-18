package com.project.nit3213.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.nit3213.data.model.LoginResponse
import com.project.nit3213.data.repository.AuthRepository
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

// Unit tests for LoginViewModel using JUnit4, Mockito, and Coroutines Test Dispatcher
@ExperimentalCoroutinesApi
class LoginViewModelTest {

    // Swaps background executor used by Architecture Components with synchronous one
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: AuthRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Replace Main dispatcher with test dispatcher
        repository = mock()                  // Create mock AuthRepository instance
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset Main dispatcher after test execution
    }

    // Tests that blank username/password inputs produce LoginState.Error
    @Test
    fun login_blankFields_emitsError() {
        viewModel.login("", "")
        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Error)
    }

    // Tests that a successful API response emits LoginState.Success with keypass
    @Test
    fun login_success_emitsSuccess() = runTest {
        val mockResponse = Response.success(LoginResponse("topicName"))
        whenever(repository.login(any(), any())).thenReturn(mockResponse)

        viewModel.login("8117545", "Ankit")
        testDispatcher.scheduler.advanceUntilIdle() // Execute pending coroutines

        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Success)
        assertEquals("topicName", (state as LoginState.Success).keypass)
    }
}

