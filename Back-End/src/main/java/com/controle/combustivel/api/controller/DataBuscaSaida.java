package com.controle.combustivel.api.controller;

import java.time.OffsetDateTime;

import lombok.Data;
@Data
public class DataBuscaSaida {
	private OffsetDateTime dataInicio;
	private OffsetDateTime dataFim;

}
