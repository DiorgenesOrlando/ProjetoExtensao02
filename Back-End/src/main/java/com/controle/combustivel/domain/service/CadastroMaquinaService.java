package com.controle.combustivel.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controle.combustivel.api.exception.EntidadeNaoEncontradaException;
import com.controle.combustivel.domain.model.Combustivel;
import com.controle.combustivel.domain.model.Maquina;
import com.controle.combustivel.domain.repository.MaquinaRepository;

@Service
public class CadastroMaquinaService {
	@Autowired
	private MaquinaRepository maquinaRepository;
	@Autowired
	private CadastroCombustivelService cadastroCombustivel;	
	public Maquina salvar(Maquina maquina) {

		long combustivelId = maquina.getCombustivel().getId();

		Combustivel combustivel = cadastroCombustivel.buscar(combustivelId);
		maquina.setCombustivel(combustivel);		

		return maquinaRepository.save(maquina); 
	}
	public Maquina buscar(long maquinaId) {
		return maquinaRepository.findById(maquinaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(maquinaId) );
	}
	public List<Maquina> listar() {
		return maquinaRepository.findAll();
	}
	public List<Maquina> listarAtivos() {
		List<Maquina> todasMaquinas = maquinaRepository.findAll();
		List<Maquina> ativos = new ArrayList<Maquina>();

		for(Maquina m : todasMaquinas) {
			if(m.isAtivo() == true) {
				ativos.add(m);	
			}
		}
		return ativos;
	}
	public List<Maquina> listarInativos() {
		List<Maquina> todasMaquinas = maquinaRepository.findAll();
		List<Maquina> ativos = new ArrayList<Maquina>();

		for(Maquina m : todasMaquinas) {
			if(m.isAtivo() == false) {
				ativos.add(m);	
			}
		}
		return ativos;
	}
}
