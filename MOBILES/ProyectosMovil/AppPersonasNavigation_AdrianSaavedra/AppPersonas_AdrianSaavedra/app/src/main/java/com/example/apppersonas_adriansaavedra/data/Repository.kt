package com.example.apppersonas_adriansaavedra.data

import com.example.apppersonas_adriansaavedra.domain.modelo.Persona


object Repository {
    private val personas = mutableListOf<Persona>()

    init {
        val persona1 = Persona(1,"Adrian", "adri@gmail", 171, "messi", "20/09/2024", "Masculino", true)
        val persona2 =
            Persona(2,
                "Juan",
                "juan@gmail",
                (151..200).random(),
                "20122022",
                "12/01/2002",
                "Masculino",
                true
            )
        val persona3 = Persona(3,
            "Maria",
            "garcia@gmail",
            (151..200).random(),
            "18122022",
            "20/09/2004",
            "Femenino",
            true
        )
        val persona4 = Persona(4,
            "Pedro",
            "lopez@gmail",
            (151..200).random(),
            "18122022",
            "12/06/2006",
            "Masculino",
            true
        )
        val persona5 =
            Persona(5,
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

    fun addPerson(persona: Persona): Boolean {
        persona.id = getAutoId()
        return personas.add(persona)
    }

    fun getPersonas(): List<Persona> {
        return personas.toList()
    }

    fun getPersona(id: Int): Persona {
        return personas.find { it.id == id } ?: Persona()
    }

    fun deletePersona(persona: Persona): Boolean {
        return personas.remove(persona)
    }

    fun updatePersona(persona: Persona, newPersona: Persona) {
        newPersona.id = persona.id
        val index = personas.indexOfFirst { it.id == persona.id }
        if (index != -1) {
            personas[index] = newPersona
        }
    }


    private fun getAutoId(): Int {
        val maxId = personas.maxByOrNull { it.id }?.id ?: 0
        return maxId + 1
    }


}