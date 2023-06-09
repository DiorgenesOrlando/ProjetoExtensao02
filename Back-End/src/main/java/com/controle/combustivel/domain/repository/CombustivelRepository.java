package com.controle.combustivel.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.controle.combustivel.domain.model.Combustivel;

public interface CombustivelRepository extends JpaRepository<Combustivel, Long> {

}
