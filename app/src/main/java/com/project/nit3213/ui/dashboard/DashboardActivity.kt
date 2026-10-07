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

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: DashboardAdapter

    companion object {
        const val EXTRA_KEYPASS = "extra_keypass"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val keypass = intent.getStringExtra(EXTRA_KEYPASS) ?: ""

        setupRecyclerView()

        viewModel.dashboardState.observe(this) { state ->
            when (state) {
                is DashboardState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                }
                is DashboardState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(state.entities)
                }
                is DashboardState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = state.message
                }
            }
        }

        if (keypass.isNotBlank()) {
            viewModel.fetchDashboard(keypass)
        } else {
            binding.tvError.visibility = View.VISIBLE
            binding.tvError.text = "Invalid keypass provided."
        }
    }

    private fun setupRecyclerView() {
        adapter = DashboardAdapter { entity ->
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_ENTITY, entity)
            }
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}
