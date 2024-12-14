package com.example.uas_ppapba_syafiqabdillahhabib_514688.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.uas_ppapba_syafiqabdillahhabib_514688.AuthManager
import com.example.uas_ppapba_syafiqabdillahhabib_514688.ViewModel.AuthViewModel
import com.example.uas_ppapba_syafiqabdillahhabib_514688.databinding.ActivityLoginBinding
import com.example.uas_ppapba_syafiqabdillahhabib_514688.fragment.HomeFragment
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.authState.observe(this) { state ->
            when (state) {
                is AuthViewModel.AuthState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is AuthViewModel.AuthState.Success -> {
                    binding.progressBar.isVisible = false
                    val intent = Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        // Pass user role if needed
                        putExtra("USER_ROLE", state.user?.role)
                    }
                    Log.d("HomeFragment", "isAdmin: ${state.user?.role}")
                    if (state.user?.role == "ADMIN") {
                        val authManager = AuthManager(this)
                        authManager.isAdmin = true
                    }
                    AuthManager(this).setLoggedIn(true)
                    startActivity(intent)
                    finish()
                }
                is AuthViewModel.AuthState.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                viewModel.login(username, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}