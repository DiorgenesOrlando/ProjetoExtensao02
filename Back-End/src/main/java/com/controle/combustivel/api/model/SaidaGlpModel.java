package com.controle.combustivel.api.model;

import lombok.Data;

@Data
public class SaidaGlpModel {
	private String centroCusto;
	private double porcentagem;
	private double quantidade;
	public SaidaGlpModel(String centroCusto, double quantidade) {
		super();
		this.centroCusto = centroCusto;
		this.quantidade = quantidade;
	}
	


}
