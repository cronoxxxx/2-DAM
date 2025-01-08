package com.example.apppersonas_adriansaavedra.domain.usecases.personas

import com.example.apppersonas_adriansaavedra.data.Repository
import javax.inject.Inject


class GetPersonUseCase @Inject constructor(){

    operator fun invoke (id:Int) = Repository.getPersona(id)
}