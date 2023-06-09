package com.controle.combustivel.domain.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
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
public class SaidaCombustivel {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Reservatorio origem;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Maquina destino;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Abastecedor abastecedor;
	private double quantidade;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Combustivel combustivel;
	@Column(nullable = false, columnDefinition = "dateTime")
	private OffsetDateTime dataHoraSaida;

	
	
}
