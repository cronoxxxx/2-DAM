package com.example.segundaapp_adriansaavedra.data

import com.example.segundaapp_adriansaavedra.domain.modelo.Persona

object Repository {
    private val personas = mutableListOf<Persona>()

    init {
        val persona1 = Persona("Adrian", "adri@gmail", 171, "messi", "20/09/2024", "Masculino", true)
        val persona2 =
            Persona(
                "Juan",
                "juan@gmail",
                (151..200).random(),
                "20122022",
                "12/01/2002",
                "Masculino",
                true
            )
        val persona3 = Persona(
            "Maria",
            "garcia@gmail",
            (151..200).random(),
            "18122022",
            "20/09/2004",
            "Femenino",
            true
        )
        val persona4 = Persona(
            "Pedro",
            "lopez@gmail",
            (151..200).random(),
            "18122022",
            "12/06/2006",
            "Masculino",
            true
        )
        val persona5 =
            Persona(
                "Ana",
                "sanchez@gmail",
                (151..200).random(),
                "18122022",
                "25/12/2008",
                "Femenino",
                true
            )
        personas.add(persona1)
        personas.add(persona2)
        personas.add(persona3)
        personas.add(persona4)
        personas.add(persona5)

    }

    fun addPerson(persona: Persona): Boolean =
        personas.add(persona)

    fun getPersonas(): List<Persona> {
        return personas
    }

    fun getPersona(id: Int): Persona {
        if (personas.isEmpty()) {
            return Persona()
        }
        if (id >= 0 && id <= personas.size - 1) {
            return personas[id]
        }
        return Persona()
    }

    fun deletePersona(id: Int): Boolean {
        return personas.remove(personas[id])
    }

    fun updatePersona(persona: Persona, newPersona: Persona) {
        personas[personas.indexOf(persona)] = newPersona
    }


}