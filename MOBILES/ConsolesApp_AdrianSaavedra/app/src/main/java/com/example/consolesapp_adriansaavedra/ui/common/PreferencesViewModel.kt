package com.example.consolesapp_adriansaavedra.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consolesapp_adriansaavedra.data.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel(){
    val playerId = preferencesRepository.userId.stateIn(
        viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = 0
    )
}