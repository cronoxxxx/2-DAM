package com.example.segundaapp_adriansaavedra.domain.usecases.personas

import com.example.segundaapp_adriansaavedra.data.Repository


class GetPersonUseCase {

    operator fun invoke (id:Int) = Repository.getPersona(id)
}