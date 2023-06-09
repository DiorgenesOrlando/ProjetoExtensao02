package com.example.coletorjetpackcompose.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EntradaDadosViewModel : ViewModel(){
    private var _mainScreenUiState : MutableStateFlow<MainScreenUiState> = MutableStateFlow(
        MainScreenUiState()
    )
     private var _entradaDadosUiState : MutableStateFlow<EntradaDadosUiState> = MutableStateFlow(EntradaDadosUiState())
     val entradaDadosUiState : StateFlow<EntradaDadosUiState> = _entradaDadosUiState.asStateFlow()
}