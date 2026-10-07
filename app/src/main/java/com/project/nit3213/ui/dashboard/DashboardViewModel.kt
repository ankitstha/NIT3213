package com.project.nit3213.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.nit3213.data.model.EntityItem
import com.project.nit3213.data.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _dashboardState = MutableLiveData<DashboardState>()
    val dashboardState: LiveData<DashboardState> = _dashboardState

    fun fetchDashboard(keypass: String) {
        _dashboardState.value = DashboardState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getDashboard(keypass)
                if (response.isSuccessful) {
                    val body = response.body()
                    val entities = body?.entities
                    if (entities != null) {
                        _dashboardState.value = DashboardState.Success(entities)
                    } else {
                        _dashboardState.value = DashboardState.Error("No entities found")
                    }
                } else {
                    val errorMsg = response.errorBody()?.string() ?: response.message()
                    _dashboardState.value = DashboardState.Error("Failed to load dashboard: $errorMsg")
                }
            } catch (e: Exception) {
                _dashboardState.value = DashboardState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }
}

sealed class DashboardState {
    object Loading : DashboardState()
    data class Success(val entities: List<EntityItem>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}
