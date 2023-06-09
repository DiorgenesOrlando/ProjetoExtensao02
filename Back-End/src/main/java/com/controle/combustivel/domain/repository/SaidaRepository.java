package com.controle.combustivel.domain.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.controle.combustivel.api.model.ConsumoPorMaquina;
import com.controle.combustivel.api.model.SaidaGlpModel;
import com.controle.combustivel.domain.model.SaidaCombustivel;

public interface SaidaRepository extends JpaRepository<SaidaCombustivel, Long> {
//	@Query("select s.destino.codigoSap, s.destino.centroCusto , SUM(s.quantidade) from SaidaCombustivel s where s.dataHoraSaida >=:dataInicio "
//			+ "and s.dataHoraSaida <= :dataFim and s.combustivel.id =:combustivelId"
//			+ " group by s.destino.codigoSap")
//	"select s.destino.codigoSap, s.destino.nomeEquipamento, s.origem.id, s.quantidade, s.dataHoraSaida, "
//	+ "s.abastecedor.nome, s.combustivel.nome from SaidaCombustivel s where s.dataHoraSaida >=:dataInicio "
//	+ "and s.dataHoraSaida <= :dataFim and s.combustivel.id = 2"
//	+ " group by s.destino.codigoSap"
	@Query("select s from SaidaCombustivel s where s.dataHoraSaida >=:dataInicio "
			+ "and s.dataHoraSaida <= :dataFim and s.combustivel.id = 2")
	
	List<SaidaCombustivel> filtrarConsumoDiesel(OffsetDateTime dataInicio, OffsetDateTime dataFim);
	@Query("select new com.controle.combustivel.api.model.SaidaGlpModel(s.destino.centroCusto, SUM(s.quantidade)) from SaidaCombustivel s where s.dataHoraSaida >=:dataInicio "
			+ "and s.dataHoraSaida <= :dataFim and s.combustivel.id = 3"
			+ " group by s.destino.codigoSap")
	List<SaidaGlpModel> filtrarConsumoGlp(OffsetDateTime dataInicio, OffsetDateTime dataFim);
	
	@Query("select new com.controle.combustivel.api.model.ConsumoPorMaquina(s.destino.codigoSap, "
			+ "SUM(s.quantidade), s.destino.nomeEquipamento, s.destino.centroCusto, s.dataHoraSaida, s.destino.ordemInterna)"
			+ " from SaidaCombustivel s where s.dataHoraSaida >=:dataInicio "
			+ "and s.dataHoraSaida <= :dataFim "
			+ " group by s.destino.codigoSap")
	List<ConsumoPorMaquina> filtrarConsumoPorMaquinaAgregado(OffsetDateTime dataInicio, OffsetDateTime dataFim);	
	@Query("select new com.controle.combustivel.api.model.ConsumoPorMaquina(s.id, s.origem.id, s.destino.codigoSap, "
			+ "s.quantidade, s.destino.nomeEquipamento, s.destino.centroCusto, s.dataHoraSaida, s.destino.ordemInterna)"
			+ " from SaidaCombustivel s where s.dataHoraSaida >=:dataInicio "
			+ "and s.dataHoraSaida <= :dataFim order by s.id")
			
	List<ConsumoPorMaquina> filtrarConsumoPorMaquina(OffsetDateTime dataInicio, OffsetDateTime dataFim);
}
