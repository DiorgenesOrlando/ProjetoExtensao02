package com.controle.combustivel.domain.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controle.combustivel.api.exception.EntidadeNaoEncontradaException;
import com.controle.combustivel.api.model.ConsumoPorMaquina;
import com.controle.combustivel.api.model.SaidaDieselModel;
import com.controle.combustivel.api.model.SaidaGlpModel;
import com.controle.combustivel.domain.model.Maquina;
import com.controle.combustivel.domain.model.Reservatorio;
import com.controle.combustivel.domain.model.SaidaCombustivel;
import com.controle.combustivel.domain.repository.SaidaRepository;

@Service
public class CadastroSaidaService {
	@Autowired
	private SaidaRepository saidaRepository;
	@Autowired
	private CadastroMaquinaService maquinaService;
	@Autowired
	private CadastroReservatorioService reservatorioService;
	
	public SaidaCombustivel salvar (SaidaCombustivel saidaCombustivel) {
		return saidaRepository.save(saidaCombustivel); 
	}
	
	public List<SaidaCombustivel> listar () {
		return saidaRepository.findAll();
		
	}
	public List<SaidaDieselModel> filtrarConsumoDiesel( OffsetDateTime dataInicio, OffsetDateTime dataFim) {
		List<SaidaCombustivel> listaConsumo = saidaRepository.filtrarConsumoDiesel(dataInicio, dataFim);
		List<SaidaDieselModel> listaDieselModel = toDieselModel(listaConsumo);
		
		
		return listaDieselModel;
	}

	private List<SaidaDieselModel> toDieselModel(List<SaidaCombustivel> listaConsumo) {
		List <SaidaDieselModel> listaDieselModel = new ArrayList<SaidaDieselModel>();
		
		for(SaidaCombustivel l : listaConsumo) {
			SaidaDieselModel model = new SaidaDieselModel();
			model.setCodigoSap(l.getDestino().getCodigoSap());
			model.setOrdemInterna(l.getDestino().getOrdemInterna());
			model.setCombustivel(l.getCombustivel().getNome());
			model.setDataHoraSaida(l.getDataHoraSaida());
			model.setOrigemId(l.getOrigem().getId());
			model.setQuantidade(l.getQuantidade());
			model.setNomeEquipamento(l.getDestino().getNomeEquipamento());
			model.setNomeAbastecedor(l.getAbastecedor().getNome());
			model.setCentroCusto(l.getDestino().getCentroCusto());
			listaDieselModel.add(model);				
		}
	
		return listaDieselModel;
	}
	public void excluir(long id) {
		
		Optional<SaidaCombustivel> saidaCombustivel = saidaRepository.findById(id);
		double valorEstorno = saidaCombustivel.get().getQuantidade();
		long idOrigem = saidaCombustivel.get().getOrigem().getId();
		
		Reservatorio origem = reservatorioService.buscar(idOrigem);
		origem.setVolume(origem.getVolume()+valorEstorno);
		reservatorioService.atualizar(origem);
		
		saidaRepository.deleteById(id);
		saidaRepository.flush();
		
	}
	
	public List<?> filtrarConsumoPorMaquinaAgregado( OffsetDateTime dataInicio, OffsetDateTime dataFim) {
		List<ConsumoPorMaquina> lista = saidaRepository.filtrarConsumoPorMaquinaAgregado(dataInicio, dataFim);
		return lista; 

	}
	public List<?> filtrarConsumoPorMaquina( OffsetDateTime dataInicio, OffsetDateTime dataFim) {
		List<ConsumoPorMaquina> lista = saidaRepository.filtrarConsumoPorMaquina(dataInicio, dataFim);
		return lista; 

	}
	
	
	public List<?> filtrarConsumoGlp( OffsetDateTime dataInicio, OffsetDateTime dataFim) {
		return calculoPorcentagemGlp(dataInicio, dataFim);
	}
	public List<SaidaGlpModel> calculoPorcentagemGlp(OffsetDateTime dataInicio, OffsetDateTime dataFim){
		double totalGlpConsumido = 0;
		List<SaidaGlpModel> lista = saidaRepository.filtrarConsumoGlp(dataInicio, dataFim);
		// obtem o total de glp consumido
		for(SaidaGlpModel saida : lista) {
			totalGlpConsumido += saida.getQuantidade();
		}
		for(SaidaGlpModel saida : lista) {
			saida.setPorcentagem(Math.round((saida.getQuantidade()*100) / totalGlpConsumido ));
			lista.set(lista.indexOf(saida), saida);
		}
		return lista;
	}

	public SaidaCombustivel buscar(Long id) {
		
		return saidaRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(id));
	}
	
	public SaidaCombustivel editar (SaidaCombustivel saidaCombustivel) {
		Long codigoSap = saidaCombustivel.getDestino().getCodigoSap();
		Long reservatorioId = saidaCombustivel.getOrigem().getId();
		Maquina maquina = maquinaService.buscar(codigoSap);
		Reservatorio reservatorio = reservatorioService.buscar(reservatorioId);
		double quantidadeCombustivelAnterior = maquina.getVolumeTanque();
		
		double diferenca = saidaCombustivel.getQuantidade()- quantidadeCombustivelAnterior ;
		System.out.println(diferenca);
		
			reservatorio.setVolume(reservatorio.getVolume()+diferenca);
			
			maquina.setVolumeTanque(saidaCombustivel.getQuantidade());
			reservatorioService.salvar(reservatorio);
			maquinaService.salvar(maquina);
			return saidaRepository.save(saidaCombustivel);
		
	}
}




