package com.controle.combustivel.api.model;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class ConsumoPorMaquina {
	private long id;
	private long origemId;

	private long codigoSap;
	private String centroCusto;
	private String ordemInterna;

	private String nomeEquipamento;
	private OffsetDateTime dataHoraSaida;


	private double quantidade;
	public ConsumoPorMaquina(long codigoSap, double quantidade, String nomeEquipamento,String centroCusto,
			OffsetDateTime dataHoraSaida, String ordemInterna
) {
		super();
		this.codigoSap = codigoSap;
		this.nomeEquipamento = nomeEquipamento;
		this.centroCusto = centroCusto;
		this.quantidade = quantidade;
		this.dataHoraSaida = dataHoraSaida;
		this.ordemInterna = ordemInterna;
	}
	public ConsumoPorMaquina(long id, long origemId, long codigoSap, double quantidade, String nomeEquipamento,String centroCusto,
			OffsetDateTime dataHoraSaida, String ordemInterna
) {
		super();
		this.id = id;
		this.origemId = origemId;
		this.codigoSap = codigoSap;
		this.nomeEquipamento = nomeEquipamento;
		this.centroCusto = centroCusto;
		this.quantidade = quantidade;
		this.dataHoraSaida = dataHoraSaida;
		this.ordemInterna = ordemInterna;
	}
	


}
