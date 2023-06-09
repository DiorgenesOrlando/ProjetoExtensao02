package com.controle.combustivel.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.controle.combustivel.domain.model.Maquina;
import com.controle.combustivel.domain.model.MaquinaSnap;

public interface MaquinaSnapRepository extends JpaRepository<MaquinaSnap, Long> {
	

}
