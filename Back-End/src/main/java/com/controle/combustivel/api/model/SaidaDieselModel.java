package com.controle.combustivel.api.model;

import java.time.OffsetDateTime;

import lombok.Data;
@Data
public class SaidaDieselModel {

	private long codigoSap;
	private String ordemInterna;
	private String centroCusto;
	private String nomeEquipamento;
	private long origemId;
	private double quantidade;
	private OffsetDateTime dataHoraSaida;
	private String nomeAbastecedor;
	private String combustivel;
}

