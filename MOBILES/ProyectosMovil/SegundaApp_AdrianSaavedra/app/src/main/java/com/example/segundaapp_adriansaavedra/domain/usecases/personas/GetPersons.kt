package com.example.segundaapp_adriansaavedra.domain.usecases.personas

import com.example.segundaapp_adriansaavedra.data.Repository

class GetPersons {
    operator fun invoke () = Repository.getPersonas()

}
