package com.ondinnonk.newsapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ondinnonk.newsapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            sharedViewModel.openDetails.collectLatest {
                if (it != null && isLandscape()) {
                    showRightContainer()
                }
            }
        }
        lifecycleScope.launch {
            sharedViewModel.closeDetails.collectLatest {
                if (isLandscape()) {
                    hideRightContainer()
                }
            }
        }
    }

    private fun showRightContainer() {
        if (isLandscape().not()) return
        //later maybe add animation
        binding.navFragmentRight?.visibility = View.VISIBLE
    }

    private fun hideRightContainer() {
        if (isLandscape().not()) return
        binding.navFragmentRight?.visibility = View.GONE

    }

    private fun isLandscape() = baseContext.isLandscape()
}