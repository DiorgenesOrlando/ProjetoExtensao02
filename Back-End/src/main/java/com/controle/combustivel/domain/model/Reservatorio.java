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
public class Reservatorio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private float porcentagem;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Combustivel combustivel;
	private double volume;
	private boolean ativo = true;

	 

}
