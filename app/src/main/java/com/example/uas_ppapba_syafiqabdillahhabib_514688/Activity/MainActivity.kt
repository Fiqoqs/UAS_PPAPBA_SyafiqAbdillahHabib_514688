package com.example.uas_ppapba_syafiqabdillahhabib_514688.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.uas_ppapba_syafiqabdillahhabib_514688.AuthManager
import com.example.uas_ppapba_syafiqabdillahhabib_514688.R
import com.example.uas_ppapba_syafiqabdillahhabib_514688.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Bottom Navigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
        checkLoginStatus()

    }
    private fun checkLoginStatus() {
        val isLoggedIn = AuthManager(this).isLoggedIn()
        if (!isLoggedIn) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }
}