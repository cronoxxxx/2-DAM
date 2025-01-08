package com.example.apppersonas_adriansaavedra.ui.pantallaMain


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apppersonas_adriansaavedra.R

import com.example.apppersonas_adriansaavedra.databinding.FragmentMainBinding
import com.example.apppersonas_adriansaavedra.ui.Constantes

import com.example.apppersonas_adriansaavedra.ui.common.MarginItemDecoration

import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var personasAdapter: PersonasAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d(Constantes.NAVEGANDO_AL_FRAGMENT_PRINCIPAL)
        personasAdapter = PersonasAdapter(::click)
        with(binding) {
            rvPersonas.adapter = personasAdapter
            rvPersonas.layoutManager = GridLayoutManager(requireContext(), 1)
            rvPersonas.addItemDecoration(MarginItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin)
            ))
        }

        binding.fabAddPerson.setOnClickListener {
            Timber.d(Constantes.NAVEGANDO_AL_FRAGMENT_DE_AGREGAR_PERSONA)
            val action = MainFragmentDirections.actionMainFragmentToAddPersonaFragment()
            findNavController().navigate(action)
        }

        observarViewModel()

        viewModel.handleEvent(MainEvent.GetPersonas)
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.aviso?.let { aviso ->
                Toast.makeText(requireContext(), aviso, Toast.LENGTH_SHORT).show()
                viewModel.handleEvent(MainEvent.ErrorVisto)
            }
            personasAdapter.submitList(state.personas)
        }
    }

    private fun click(id: Int) {
        Timber.d(Constantes.NAVEGANDO_AL_FRAGMENT_DE_PERSONA_CON_ID + id)
        val action = MainFragmentDirections.actionMainFragmentToPersonaFragment(id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}