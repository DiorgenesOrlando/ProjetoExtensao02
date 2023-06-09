package com.controle.combustivel.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controle.combustivel.api.exception.EntidadeNaoEncontradaException;
import com.controle.combustivel.domain.model.Abastecedor;
import com.controle.combustivel.domain.repository.AbastecedorRepository;

@Service
public class CadastroAbastecedorService {
	@Autowired
	private AbastecedorRepository abastecedorRepository;

	public Abastecedor salvar(Abastecedor abastecedor) {
		return abastecedorRepository.save(abastecedor);
	}
	public Abastecedor buscar(long abastecedorId) {
		return abastecedorRepository.findById(abastecedorId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(abastecedorId) );
	}
	public List<Abastecedor> listar(){
		return abastecedorRepository.findAll();
	}
	public List<Abastecedor> listarAtivos(){
		List<Abastecedor> todos = abastecedorRepository.findAll();
		List<Abastecedor> ativos = new ArrayList<Abastecedor>();
		for(Abastecedor a : todos) {
			if(a.isAtivo() == true) {
				ativos.add(a);
				System.out.println(a.getNome());
			}
		}
		return ativos;
	}
	public List<Abastecedor> listarInativos(){
		List<Abastecedor> todos = abastecedorRepository.findAll();
		List<Abastecedor> ativos = new ArrayList<Abastecedor>();
		for(Abastecedor a : todos) {
			if(a.isAtivo() == false) {
				ativos.add(a);
				System.out.println(a.getNome());
			}
		}
		return ativos;
	}
	public void excluir () {

	}

}
