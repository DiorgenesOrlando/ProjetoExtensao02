package com.controle.combustivel.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.controle.combustivel.api.exception.EntidadeNaoEncontradaException;
import com.controle.combustivel.domain.model.Combustivel;
import com.controle.combustivel.domain.model.Reservatorio;
import com.controle.combustivel.domain.repository.ReservatorioRepository;

@Service
public class CadastroReservatorioService {

	@Autowired
	private ReservatorioRepository reservatorioRepository;
	@Autowired
	private CadastroCombustivelService cadastroCombustivel;

	public Reservatorio salvar(Reservatorio reservatorio) {
		return reservatorioRepository.save(reservatorio);
	}

	public Reservatorio atualizar(Reservatorio reservatorio) {

		long combustivelId = reservatorio.getCombustivel().getId();		
		Combustivel combustivel = cadastroCombustivel.buscar(combustivelId);
		reservatorio.setCombustivel(combustivel);

		return reservatorioRepository.save(reservatorio);
	}
	public Reservatorio atualizarVolume(float volume, Long id) {

		Reservatorio reservatorio = reservatorioRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(id) );
		reservatorio.setVolume(volume);
		return reservatorioRepository.save(reservatorio);
	}
	
	public Reservatorio atualizarPorcentagem(float porcentagem, Long id) {

		Reservatorio reservatorio = reservatorioRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(id) );
		reservatorio.setPorcentagem(porcentagem);
		return reservatorioRepository.save(reservatorio);
	}

	public Reservatorio buscar(long reservatorioId) {
		return reservatorioRepository.findById(reservatorioId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(reservatorioId) );
	}

	public List<Reservatorio> listar() {
		return reservatorioRepository.findAll();
	}
	public List<Reservatorio> listarAtivos() {
		return reservatorioRepository.findAllByAtivo(true);
	}
	public List<Reservatorio> listarPorCombustivel(Long id) {
		// TODO Auto-generated method stub
		return reservatorioRepository.findAllByCombustivelIdAndAtivo(id,true);
	}
	


}
