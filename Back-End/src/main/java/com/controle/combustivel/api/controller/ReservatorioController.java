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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.controle.combustivel.api.exception.EntidadeNaoEncontradaException;
import com.controle.combustivel.domain.model.Reservatorio;
import com.controle.combustivel.domain.service.CadastroReservatorioService;

@RestController
@RequestMapping(value = "/reservatorio")
public class ReservatorioController {

	@Autowired
	private CadastroReservatorioService cadastroReservatorio;
	@GetMapping("/{id}")
	public Reservatorio buscar (@PathVariable Long id) {
		return cadastroReservatorio.buscar(id);
	}
	
	@GetMapping()
	public List<Reservatorio> listar (){
		return cadastroReservatorio.listar();
	}
	@GetMapping("/ativos")
	public List<Reservatorio> listarAtivos(){
		return cadastroReservatorio.listarAtivos();
	}
	@GetMapping("/listarDiesel")
	public List<Reservatorio> listarDiesel (){
		return cadastroReservatorio.listarPorCombustivel(2l);
	}
	@GetMapping("/listarGlp")
	public List<Reservatorio> listarGlp (){
		return cadastroReservatorio.listarPorCombustivel(3l);
	}
	@PostMapping
	public Reservatorio salvar (@RequestBody Reservatorio reservatorio) {
		return cadastroReservatorio.salvar(reservatorio); 
	}

	@PutMapping(value = "/{id}")
	public Reservatorio Atualizar(@RequestBody Reservatorio reservtorioAtualizado, @PathVariable Long id) { 
		try {
			Reservatorio reservatorioAtual = cadastroReservatorio.buscar(id);
			BeanUtils.copyProperties(reservtorioAtualizado, reservatorioAtual, "id");

			return cadastroReservatorio.atualizar(reservatorioAtual);
		}catch(EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(id);
		}
	}
	@PutMapping("/atualizar-volume/{id}/")
	public Reservatorio atualizarVolume( @PathVariable Long id, @RequestParam float volume) {
		return cadastroReservatorio.atualizarVolume(volume, id);
	}
	@PutMapping("/atualizar-porcentagem/{id}/")
	public Reservatorio atualizarPorcentagem( @PathVariable Long id, @RequestParam float porcentagem) {
		return cadastroReservatorio.atualizarPorcentagem(porcentagem, id);

	}
	@PutMapping("/desativar/{id}")
	public void desativar (@PathVariable Long id) {
		Reservatorio reservatorio = cadastroReservatorio.buscar(id);
		reservatorio.setAtivo(false);
		cadastroReservatorio.atualizar(reservatorio);

	}
	@PutMapping("/ativar/{id}")
	public void ativar (@PathVariable Long id) {
		Reservatorio reservatorio = cadastroReservatorio.buscar(id);
		reservatorio.setAtivo(true);
		cadastroReservatorio.atualizar(reservatorio);

	}

}
