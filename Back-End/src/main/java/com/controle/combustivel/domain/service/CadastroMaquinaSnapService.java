package com.controle.combustivel.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controle.combustivel.api.exception.EntidadeNaoEncontradaException;
import com.controle.combustivel.domain.model.Combustivel;
import com.controle.combustivel.domain.model.Maquina;
import com.controle.combustivel.domain.model.MaquinaSnap;
import com.controle.combustivel.domain.repository.MaquinaRepository;
import com.controle.combustivel.domain.repository.MaquinaSnapRepository;

@Service
public class CadastroMaquinaSnapService {
	@Autowired
	private MaquinaSnapRepository maquinaSnapRepository;
	@Autowired
	private CadastroCombustivelService cadastroCombustivel;	
	
	public MaquinaSnap salvar(MaquinaSnap maquina) {	

		return maquinaSnapRepository.save(maquina); 
	}
	public MaquinaSnap buscar(long maquinaId) {
		return maquinaSnapRepository.findById(maquinaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(maquinaId) );
	}
	
}
