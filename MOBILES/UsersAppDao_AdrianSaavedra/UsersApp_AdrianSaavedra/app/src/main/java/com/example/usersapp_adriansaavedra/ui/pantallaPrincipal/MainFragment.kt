package com.example.usersapp_adriansaavedra.ui.pantallaPrincipal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.databinding.ActivityMainFragmentBinding
import com.example.usersapp_adriansaavedra.ui.common.MarginItemDecoration
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: ActivityMainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var usersAdapter: UsuariosAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityMainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.handleEvent(MainEvent.GetUsers)
        setUpMenu()
    }

    private fun setUpMenu() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                val searchItem = menu.findItem(R.id.search)
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?) = false
                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let {
                            viewModel.handleEvent(MainEvent.GetUsersFiltrados(it))
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem) =
                when (menuItem.itemId) {
                    R.id.search -> true
                    else -> false
                }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        usersAdapter = UsuariosAdapter(::click)
        binding.rvUsers.apply {
            adapter = usersAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    handleLoadingState(state.isLoading)
                    usersAdapter.submitList(state.users)
                    state.aviso?.let { handleUiEvent(state.aviso) }
                }
            }
        }
    }

    private fun handleUiEvent(event: UiEvent?) {
        if (event is UiEvent.ShowSnackbar) {
            Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
            viewModel.handleEvent(MainEvent.AvisoVisto)
        }
    }

    private fun handleLoadingState(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }


    private fun click(id: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetalleFragment(id)
        binding.root.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
