package com.mcmouse88.recycler_view_example

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import com.mcmouse88.recycler_view_example.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: error("ActivityMainBinding is null")

    private val viewModel by viewModels<MainViewModel>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        MainAdapter(onItemDeleteClick = viewModel::deleteItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupObserver()
    }

    private fun setupView() {
        binding.rvItems.adapter = adapter
        (binding.rvItems.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.rvItems.addItemDecoration(HorizontalItemDecorator())
        binding.rvItems.addItemDecoration(VerticalItemDecoration())
    }

    private fun setupObserver() {
        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach(::renderState)
            .launchIn(lifecycleScope)
    }

    private fun renderState(state: MainViewModel.ScreenState) {
        binding.tvItemCount.text = getString(R.string.total_count, state.totalCount)
        adapter.submitList(state.list)
        binding.mainProgress.isVisible = state.isLoading
        binding.tvItemCount.isVisible = !state.isLoading
        binding.rvItems.isVisible = !state.isLoading
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}