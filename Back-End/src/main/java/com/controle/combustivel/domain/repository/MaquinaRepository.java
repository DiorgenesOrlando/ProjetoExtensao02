package com.controle.combustivel.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.controle.combustivel.domain.model.Maquina;

public interface MaquinaRepository extends JpaRepository<Maquina, Long> {
	

}
