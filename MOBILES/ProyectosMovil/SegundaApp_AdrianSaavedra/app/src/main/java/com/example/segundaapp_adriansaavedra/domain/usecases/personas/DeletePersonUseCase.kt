package com.example.segundaapp_adriansaavedra.domain.usecases.personas

import com.example.segundaapp_adriansaavedra.data.Repository

class DeletePersonUseCase {
    operator fun invoke (index:Int)=
        Repository.deletePersona(index)
}