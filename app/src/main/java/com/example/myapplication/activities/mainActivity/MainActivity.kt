package com.example.myapplication.activities.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.activities.mainActivity.history.HistoryFragment
import com.example.myapplication.activities.mainActivity.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val cocktailFragment = HomeFragment()
    private val historyFragment = HistoryFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val container = R.id.nav_host_fragment

        supportFragmentManager.beginTransaction()
                .add(container, historyFragment).hide(historyFragment)
                .add(container, cocktailFragment)
                .commit()

        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_main -> {
                    supportFragmentManager.beginTransaction().hide(historyFragment)
                            .show(cocktailFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_history -> {
                    historyFragment.refreshInfo()
                    supportFragmentManager.beginTransaction().hide(cocktailFragment)
                            .show(historyFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

}