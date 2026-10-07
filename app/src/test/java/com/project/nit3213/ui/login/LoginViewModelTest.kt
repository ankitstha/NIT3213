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

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: AuthRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun login_blankFields_emitsError() {
        viewModel.login("", "")
        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Error)
    }

    @Test
    fun login_success_emitsSuccess() = runTest {
        val mockResponse = Response.success(LoginResponse("topicName"))
        whenever(repository.login(any(), any())).thenReturn(mockResponse)

        viewModel.login("Ankit", "s8117545")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Success)
        assertEquals("topicName", (state as LoginState.Success).keypass)
    }
}
