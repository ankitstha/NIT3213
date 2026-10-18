package com.project.nit3213.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.nit3213.databinding.ActivityLoginBinding
import com.project.nit3213.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

// Main entry Activity for student authentication
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels() // Injects ViewModel via Hilt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater) // Inflates view binding
        setContentView(binding.root)

        // Handles sign-in button click
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.login(username, password)
        }

        // Observes login state changes from LoginViewModel
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE // Show loading spinner
                    binding.btnLogin.isEnabled = false            // Disable button during network call
                    binding.tvError.text = ""                     // Clear previous errors
                }
                is LoginState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    // Navigate to DashboardActivity with the returned keypass
                    val intent = Intent(this, DashboardActivity::class.java).apply {
                        putExtra(DashboardActivity.EXTRA_KEYPASS, state.keypass)
                    }
                    startActivity(intent)
                    finish() // Close login screen
                }
                is LoginState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    binding.tvError.text = state.message // Display error message
                }
            }
        }
    }
}

