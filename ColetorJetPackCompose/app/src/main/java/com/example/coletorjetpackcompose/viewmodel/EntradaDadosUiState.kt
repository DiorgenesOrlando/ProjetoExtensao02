package com.example.coletorjetpackcompose.viewmodel

import com.example.coletorjetpackcompose.model.SaidaCombustivel

data class EntradaDadosUiState(
    val dados : SaidaCombustivel =
        SaidaCombustivel(origem = "Origem", destino = "Destino")
)
//http://localhost:8080/saida-combustivel