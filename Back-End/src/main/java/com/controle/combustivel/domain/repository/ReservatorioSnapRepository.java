package com.controle.combustivel.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.controle.combustivel.domain.model.Reservatorio;
import com.controle.combustivel.domain.model.ReservatorioSnap;

public interface ReservatorioSnapRepository extends JpaRepository<ReservatorioSnap, Long> {

	//List<ReservatorioSnap> findAllByCombustivelIdAndAtivo(Long id, boolean ativo);

}
