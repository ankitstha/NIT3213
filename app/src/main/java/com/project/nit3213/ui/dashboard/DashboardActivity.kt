package com.project.nit3213.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.nit3213.databinding.ActivityDashboardBinding
import com.project.nit3213.ui.details.DetailsActivity
import dagger.hilt.android.AndroidEntryPoint

// Activity displaying list of dashboard entities
@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels() // Injects ViewModel via Hilt
    private lateinit var adapter: DashboardAdapter             // RecyclerView ListAdapter

    companion object {
        const val EXTRA_KEYPASS = "extra_keypass" // Intent extra key for keypass string
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve keypass passed from LoginActivity
        val keypass = intent.getStringExtra(EXTRA_KEYPASS) ?: ""

        setupRecyclerView() // Configure RecyclerView layout manager and adapter click listener

        // Observe dashboard state emissions from ViewModel
        viewModel.dashboardState.observe(this) { state ->
            when (state) {
                is DashboardState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                }
                is DashboardState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(state.entities) // Submit entities list to ListAdapter
                }
                is DashboardState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = state.message
                }
            }
        }

        // Fetch dashboard data if keypass is present
        if (keypass.isNotBlank()) {
            viewModel.fetchDashboard(keypass)
        } else {
            binding.tvError.visibility = View.VISIBLE
            binding.tvError.text = "Invalid keypass provided."
        }
    }

    // Configures RecyclerView adapter and item click listener to launch DetailsActivity
    private fun setupRecyclerView() {
        adapter = DashboardAdapter { entity ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_ENTITY, entity) // Pass selected entity to DetailsActivity
            }
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}

