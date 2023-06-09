package com.controle.combustivel.api.controller;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controle.combustivel.api.exception.EntidadeNaoEncontradaException;
import com.controle.combustivel.domain.model.Abastecedor;
import com.controle.combustivel.domain.service.CadastroAbastecedorService;


@RestController
@RequestMapping(value = "/abastecedores")
public class AbastecedorController {
	@Autowired
	private CadastroAbastecedorService cadastroAbastecedor;
	
	@GetMapping("/{id}")
	public Abastecedor buscar (@PathVariable Long id) {
		return cadastroAbastecedor.buscar(id);
	}
	
	@GetMapping
	public List<Abastecedor> listar(){
		return cadastroAbastecedor.listar();
	}	
	@GetMapping("/ativos")
	public List<Abastecedor> listarAtivos(){
		return cadastroAbastecedor.listarAtivos();
	}	
	@GetMapping("/inativos")
	public List<Abastecedor> listarInativos(){
		return cadastroAbastecedor.listarInativos();
	}	
	@PostMapping
	public Abastecedor salvar (@RequestBody Abastecedor abastecedor) {
		return cadastroAbastecedor.salvar(abastecedor);
	}
	
	@PutMapping("/{abastecedorId}")
	public Abastecedor atualizar (@PathVariable long abastecedorId, @RequestBody Abastecedor abastecedorAtualizado) {
		
		try {
			Abastecedor abastecedorAtual = cadastroAbastecedor.buscar(abastecedorId);
			BeanUtils.copyProperties(abastecedorAtualizado, abastecedorAtual, "id");
			return cadastroAbastecedor.salvar(abastecedorAtual);
			
		}catch (EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(abastecedorId);
		}
		
	}
	@PutMapping("/desativar/{abastecedorId}")
	public void desativar(@PathVariable Long abastecedorId) {
		try {
			Abastecedor abastecedor = cadastroAbastecedor.buscar(abastecedorId);
			abastecedor.setAtivo(false);
			cadastroAbastecedor.salvar(abastecedor);

		}catch (EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(abastecedorId);
		}
	}
	@PutMapping("/ativar/{abastecedorId}")
	public void ativar(@PathVariable Long abastecedorId) {
		try {
			Abastecedor abastecedor = cadastroAbastecedor.buscar(abastecedorId);
			abastecedor.setAtivo(true);
			cadastroAbastecedor.salvar(abastecedor);

		}catch (EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(abastecedorId);
		}
	}
}
