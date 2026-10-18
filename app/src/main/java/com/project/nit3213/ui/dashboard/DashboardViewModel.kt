package com.project.nit3213.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.nit3213.data.model.EntityItem
import com.project.nit3213.data.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

// ViewModel managing UI state and business logic for the dashboard
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository // Injected DashboardRepository instance
) : ViewModel() {

    // Internal mutable LiveData state
    private val _dashboardState = MutableLiveData<DashboardState>()
    // Public immutable LiveData exposed to DashboardActivity
    val dashboardState: LiveData<DashboardState> = _dashboardState

    // Fetches dashboard entities using the keypass token
    fun fetchDashboard(keypass: String) {
        _dashboardState.value = DashboardState.Loading // Set UI state to Loading

        // Async network request using Coroutines
        viewModelScope.launch {
            try {
                val response = repository.getDashboard(keypass)
                if (response.isSuccessful) {
                    val body = response.body()
                    val entities = body?.entities
                    if (entities != null) {
                        _dashboardState.value = DashboardState.Success(entities) // Successfully fetched entities
                    } else {
                        _dashboardState.value = DashboardState.Error("No entities found")
                    }
                } else {
                    val errorMsg = response.errorBody()?.string() ?: response.message()
                    _dashboardState.value = DashboardState.Error("Failed to load dashboard: $errorMsg")
                }
            } catch (e: SocketTimeoutException) {
                // Friendly error for Render server cold start
                _dashboardState.value = DashboardState.Error("Connection timeout. The server is waking up (cold start). Please try again.")
            } catch (e: Exception) {
                _dashboardState.value = DashboardState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }
}

// Sealed class representing possible UI states for dashboard screen
sealed class DashboardState {
    object Loading : DashboardState()
    data class Success(val entities: List<EntityItem>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}

