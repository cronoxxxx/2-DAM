package com.example.apppersonas_adriansaavedra.domain.usecases.personas

import com.example.apppersonas_adriansaavedra.data.Repository
import javax.inject.Inject


class GetPersonsUseCase @Inject constructor() {
    operator fun invoke () = Repository.getPersonas()

}
