package com.controle.combustivel.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.controle.combustivel.api.exception.EntidadeNaoEncontradaException;
import com.controle.combustivel.domain.model.ReservatorioSnap;
import com.controle.combustivel.domain.repository.ReservatorioSnapRepository;

@Service
public class CadastroReservatorioSnapService {

	@Autowired
	private ReservatorioSnapRepository reservatorioSnapRepository;

	public ReservatorioSnap salvar(ReservatorioSnap reservatorio) {
		return reservatorioSnapRepository.save(reservatorio);
	}

	public ReservatorioSnap atualizar(ReservatorioSnap reservatorio) {

		ReservatorioSnap reservatorioAtual = reservatorioSnapRepository.findById(reservatorio.getId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException(reservatorio.getId()));	
		BeanUtils.copyProperties(reservatorio, reservatorioAtual);
		
		return reservatorioSnapRepository.save(reservatorioAtual);
	}
	


}
