package com.example.coletorjetpackcompose.model

import java.time.OffsetDateTime

data class SaidaCombustivel(

    var origem: String? = null,


    var destino: String? = null,


    var abastecedor: Int? = null,

    var quantidade: Int? = null,

    var combustivel: Int? = null,
    var combustivelText: String? = null,
    var isEnviadoVisible : Boolean = true,


    var dataHoraSaida: String? = null
)
