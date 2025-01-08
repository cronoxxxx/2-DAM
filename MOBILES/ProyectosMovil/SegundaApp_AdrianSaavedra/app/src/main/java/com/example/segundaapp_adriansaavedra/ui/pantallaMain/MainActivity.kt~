
package com.example.segundaapp_adriansaavedra.ui.pantallaMain

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import com.example.segundaapp_adriansaavedra.R
import com.example.segundaapp_adriansaavedra.databinding.ActivityMainBinding
import com.example.segundaapp_adriansaavedra.domain.modelo.Persona
import com.example.segundaapp_adriansaavedra.domain.usecases.personas.*
import com.example.segundaapp_adriansaavedra.utils.StringProvider
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddPersonUseCase(),
            GetPersons(),
            DeletePersonUseCase(),
            UpdatePersonUseCase(),
            GetPersonUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        observarViewModel()
        eventos()
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this@MainActivity) { state ->
            state.aviso?.let { aviso ->
                Toast.makeText(this@MainActivity, aviso, Toast.LENGTH_SHORT).show()
                viewModel.avisoMostrado()
            }
            pintarPersona(state)
            binding.tvIndice.text = getString(R.string.indice_format, state.indiceActual, state.longitudLista)
            binding.anteriorButton.visibility = if (state.anterior == true) View.VISIBLE else View.INVISIBLE
            binding.siguienteButton.visibility = if (state.siguiente == true) View.VISIBLE else View.INVISIBLE
            binding.updateButton.visibility = if (state.updatedelete == true) View.VISIBLE else View.INVISIBLE
            binding.deleteButton.visibility = if (state.updatedelete == true) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun pintarPersona(state: MainState) {
        with(binding) {
            etNombre.setText(state.persona.nombre)
            etEmail.setText(state.persona.email)
            slEstatura.value = state.persona.estatura.toFloat()
            etClave.setText(state.persona.clave) // Ya no necesitamos convertir a String
            btnDatePicker.text = state.persona.fechaNacimiento
            when (state.persona.genero) {
                getString(R.string.hombre) -> tglGenero.check(R.id.btnHombre)
                getString(R.string.mujer) -> tglGenero.check(R.id.btnMujer)
                else -> tglGenero.clearChecked()
            }
            cbTerms.isChecked = state.persona.aceptarTerminos
        }
    }

    private fun eventos() {
        with(binding) {
            btnDatePicker.setOnClickListener {
                showDatePickerDialog()
            }

            addButton.setOnClickListener {
                val persona = crearPersonaDesdeUI()
                viewModel.addPersona(persona)
            }

            deleteButton.setOnClickListener {
                viewModel.deletePersona()
            }

            updateButton.setOnClickListener {
                val newPersona = crearPersonaDesdeUI()
                viewModel.updatePersona(newPersona)
            }

            siguienteButton.setOnClickListener {
                viewModel.getSiguientePersona()
            }

            anteriorButton.setOnClickListener {
                viewModel.getAnteriorPersona()
            }
        }
    }

    private fun crearPersonaDesdeUI(): Persona {
        return Persona(
            nombre = binding.etNombre.text.toString(),
            email = binding.etEmail.text.toString(),
            estatura = binding.slEstatura.value.toInt(),
            clave = binding.etClave.text.toString(), // Ahora guardamos la clave como String
            fechaNacimiento = binding.btnDatePicker.text.toString(),
            genero = when (binding.tglGenero.checkedButtonId) {
                R.id.btnHombre -> getString(R.string.hombre)
                R.id.btnMujer -> getString(R.string.mujer)
                else -> ""
            },
            aceptarTerminos = binding.cbTerms.isChecked
        )
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(
            this,
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
}