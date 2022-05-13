package com.example.navcomponent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.navcomponent.databinding.Fragment1Binding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class Fragment1 : Fragment(R.layout.fragment_1) {

    private val viewModel by viewModels<SomeViewModel>()

    private var binding: Fragment1Binding? = null
    private var adapter: ItemAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = Fragment1Binding.bind(view)

        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.adapter = ItemAdapter {
            findNavController().navigate(R.id.fragment2)
        }.also {
            adapter = it
        }

        observeViewModel()
        viewModel.fetchItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        adapter = null
    }

    private fun observeViewModel() {
        viewModel.itemsFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { adapter?.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}