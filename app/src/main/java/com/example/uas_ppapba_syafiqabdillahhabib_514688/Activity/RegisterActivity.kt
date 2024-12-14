package com.example.uas_ppapba_syafiqabdillahhabib_514688.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.uas_ppapba_syafiqabdillahhabib_514688.R
import com.example.uas_ppapba_syafiqabdillahhabib_514688.ViewModel.AuthViewModel
import com.example.uas_ppapba_syafiqabdillahhabib_514688.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
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
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val role = when (binding.rgRole.checkedRadioButtonId) {
                R.id.rbAdmin -> "ADMIN"
                R.id.rbUser -> "USER"
                else -> "USER" // default role
            }

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                viewModel.register(username, password, role)
            }
        }
    }
}