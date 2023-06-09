package com.controle.combustivel.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Maquina {
	@Id
	private long codigoSap;
	private String ordemInterna;
	private String nomeEquipamento;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Combustivel combustivel;
	private int capacidade;
	private String centroCusto;
	private double volumeTanque;
	// private double consumo;

	private boolean ativo = true;


}
