package com.example.apppersonas_adriansaavedra.ui.pantallaAgregar

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.apppersonas_adriansaavedra.R
import com.example.apppersonas_adriansaavedra.databinding.FragmentAddPersonaBinding
import com.example.apppersonas_adriansaavedra.domain.modelo.Persona
import com.example.apppersonas_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
@AndroidEntryPoint
class AddPersonaFragment : Fragment() {
    private var _binding: FragmentAddPersonaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddPersonaViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddPersonaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventListeners()
        observarViewModel()
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            state.aviso?.let { event ->
                when (event) {
                    is UiEvent.ShowSnackbar -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                    is UiEvent.PopBackStack -> {
                        findNavController().popBackStack()
                    }
                }
                viewModel.handleEvent(AddPersonaEvent.ErrorVisto)
            }
        }
    }

    private fun setupEventListeners() {
        with(binding) {
            btnDatePicker.setOnClickListener {
                showDatePickerDialog()
            }

            addButton.setOnClickListener {
                val newPersona = crearPersonaDesdeUI()
                viewModel.handleEvent(AddPersonaEvent.AddPersona(newPersona))
            }
        }
    }

    private fun crearPersonaDesdeUI(): Persona {
        val persona = Persona(
            nombre = binding.etNombre.text.toString(),
            email = binding.etEmail.text.toString(),
            estatura = binding.slEstatura.value.toInt(),
            clave = binding.etClave.text.toString(),
            fechaNacimiento = binding.btnDatePicker.text.toString(),
            genero = when (binding.tglGenero.checkedButtonId) {
                R.id.btnHombre -> getString(R.string.hombre)
                R.id.btnMujer -> getString(R.string.mujer)
                else -> getString(R.string.null_string)
            },
            aceptarTerminos = binding.cbTerms.isChecked
        )
        return persona
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = formatDateText(selectedDay, selectedMonth, selectedYear)
                binding.btnDatePicker.text = formattedDate
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun formatDateText(day: Int, month: Int, year: Int): String {
        return "$day/${month + 1}/$year"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}