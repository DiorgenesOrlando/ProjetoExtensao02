package com.controle.combustivel.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.controle.combustivel.domain.model.Reservatorio;

public interface ReservatorioRepository extends JpaRepository<Reservatorio, Long> {

	List<Reservatorio> findAllByCombustivelIdAndAtivo(Long id, boolean ativo);
	List<Reservatorio> findAllByAtivo(boolean ativo);

}
