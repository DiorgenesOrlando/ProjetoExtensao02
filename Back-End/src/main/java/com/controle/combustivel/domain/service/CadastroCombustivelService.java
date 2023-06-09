package com.controle.combustivel.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controle.combustivel.api.exception.EntidadeNaoEncontradaException;
import com.controle.combustivel.domain.model.Combustivel;
import com.controle.combustivel.domain.repository.CombustivelRepository;

@Service
public class CadastroCombustivelService {

	@Autowired
	private CombustivelRepository combustivelRepository;

	public Combustivel salvar(Combustivel combustivel) {
		return combustivelRepository.save(combustivel);
	}

	public Combustivel buscar(long combustivelId) {
		return combustivelRepository.findById(combustivelId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(combustivelId) );
	}
}

