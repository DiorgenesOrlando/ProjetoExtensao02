package com.controle.combustivel.api.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.controle.combustivel.api.model.ConsumoPorMaquina;
import com.controle.combustivel.api.model.SaidaDieselModel;
import com.controle.combustivel.domain.model.Abastecedor;
import com.controle.combustivel.domain.model.Combustivel;
import com.controle.combustivel.domain.model.Maquina;
import com.controle.combustivel.domain.model.Reservatorio;
import com.controle.combustivel.domain.model.SaidaCombustivel;
import com.controle.combustivel.domain.service.CadastroAbastecedorService;
import com.controle.combustivel.domain.service.CadastroCombustivelService;
import com.controle.combustivel.domain.service.CadastroMaquinaService;
import com.controle.combustivel.domain.service.CadastroReservatorioService;
import com.controle.combustivel.domain.service.CadastroSaidaService;
import com.controle.combustivel.domain.service.CriarArquivoDiesel;

@RestController
@RequestMapping(value = "/saida-combustivel")
public class SaidaCombustivelController {

	@Autowired
	private CadastroSaidaService cadastroSaida;
	@Autowired
	private CadastroReservatorioService cadastroReservatorio;
	@Autowired
	private CadastroMaquinaService cadastroMaquina;
	@Autowired
	private CadastroCombustivelService cadastroCombustivel;
	@Autowired
	private CadastroAbastecedorService cadastroAbastecedor;
	@Autowired
	CriarArquivoDiesel criarArquivoDiesel;
	/// MELHORAR EXCEÇÃO
	@PostMapping
	public SaidaCombustivel salvar (@RequestBody SaidaCombustivel saidaCombustivel) {
		Reservatorio origem = cadastroReservatorio.buscar(saidaCombustivel.getOrigem().getId());
		// criar metodo snap
		
		origem.setVolume(origem.getVolume() - saidaCombustivel.getQuantidade());
		
		saidaCombustivel.setOrigem(origem);
		Maquina destino = cadastroMaquina.buscar(saidaCombustivel.getDestino().getCodigoSap());
		// criar metodo snap
		
		saidaCombustivel.setDestino(destino);
		Abastecedor abastecedor = cadastroAbastecedor.buscar(saidaCombustivel.getAbastecedor().getId());
		saidaCombustivel.setAbastecedor(abastecedor);
		Combustivel combustivel = cadastroCombustivel.buscar(saidaCombustivel.getCombustivel().getId());
		saidaCombustivel.setCombustivel(combustivel);
		return cadastroSaida.salvar(saidaCombustivel);
	}

	@PutMapping("/estorno/{id}")
	public void extorno () {
		
	}
	
	@GetMapping("/{id}")
	public SaidaCombustivel buscar (@PathVariable Long id) {
		//Reservatorio origem = cadastroReservatorio.buscar(saidaCombustivel.getOrigem().getId());

		return cadastroSaida.buscar(id);		
	}
	@GetMapping("/listar")
	public List<SaidaCombustivel> listar (){
		return cadastroSaida.listar();
	}
	
	@PutMapping("/editar")
	public SaidaCombustivel editar (@RequestBody SaidaCombustivel saidaCombustivel) {
		System.out.println(saidaCombustivel);
		return cadastroSaida.editar(saidaCombustivel);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroSaida.excluir(id);
	} 


	@GetMapping("/filtrar-consumo-diesel")
	public List<?> filtrarConsumoDiesel(@RequestParam String dataInicio,@RequestParam String dataFim) throws IOException{
		//long combustivelId = Integer.valueOf(id);

		OffsetDateTime dateInicio = OffsetDateTime.parse(dataInicio.concat("T00:00:00+00:00"),DateTimeFormatter.ISO_DATE_TIME);//OffsetDateTime.of(date, ZoneOffset.UTC);
		OffsetDateTime dateFim = OffsetDateTime.parse(dataFim.concat("T23:59:59+00:00"),DateTimeFormatter.ISO_DATE_TIME);//OffsetDateTime.of(date, ZoneOffset.UTC);

		System.out.println(dateInicio);

		List<SaidaDieselModel> lista = cadastroSaida.filtrarConsumoDiesel(dateInicio,dateFim);
		//criarArquivoDiesel.criarExcel(lista);
		return cadastroSaida.filtrarConsumoDiesel(dateInicio, dateFim);
	}
	
	// here ---------------------
	@GetMapping("/filtrar-consumo-diesel/download")
	public ResponseEntity<InputStreamResource>filtrarConsumoDieselDownload(@RequestParam String dataInicio,@RequestParam String dataFim) throws IOException{
		//long combustivelId = Integer.valueOf(id);

		OffsetDateTime dateInicio = OffsetDateTime.parse(dataInicio.concat("T00:00:00+00:00"),DateTimeFormatter.ISO_DATE_TIME);//OffsetDateTime.of(date, ZoneOffset.UTC);
		OffsetDateTime dateFim = OffsetDateTime.parse(dataFim.concat("T23:59:59+00:00"),DateTimeFormatter.ISO_DATE_TIME);//OffsetDateTime.of(date, ZoneOffset.UTC);
		
		List<SaidaDieselModel> lista = cadastroSaida.filtrarConsumoDiesel(dateInicio,dateFim);
		criarArquivoDiesel.criarExcel(lista);
		
		InputStream diesel = new FileInputStream("C:\\imagensApi\\controleCombustivel\\arquivo_diesel\\diesel.txt");

		
		return ResponseEntity.ok()
				.contentType(MediaType.TEXT_PLAIN	)
				.body(new InputStreamResource(diesel));	
	}
	@GetMapping("/filtrar-consumo-agregado/download")
	public ResponseEntity<InputStreamResource> filtrarConsumoAgregadoDownload(@RequestParam String dataInicio, @RequestParam String dataFim) throws IOException {
	    OffsetDateTime dateInicio = OffsetDateTime.parse(dataInicio.concat("T00:00:00+00:00"), DateTimeFormatter.ISO_DATE_TIME);
	    OffsetDateTime dateFim = OffsetDateTime.parse(dataFim.concat("T23:59:59+00:00"), DateTimeFormatter.ISO_DATE_TIME);

	    List<ConsumoPorMaquina> lista = (List<ConsumoPorMaquina>) cadastroSaida.filtrarConsumoPorMaquinaAgregado(dateInicio, dateFim);

	    // Gerar o arquivo .xls usando a lista de consumo

	    InputStream consumo = new FileInputStream("C:\\imagensApi\\controleCombustivel\\arquivo_diesel\\consumo.xls");
		criarArquivoDiesel.criarPlanilha(lista);


	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel");
	    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=consumo.xls");

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(new InputStreamResource(consumo));
	}
	
	@GetMapping("/filtrar-consumo-maquina/download")
	public ResponseEntity<InputStreamResource> filtrarConsumoMaquinaDownload(@RequestParam String dataInicio, @RequestParam String dataFim) throws IOException {
	    OffsetDateTime dateInicio = OffsetDateTime.parse(dataInicio.concat("T00:00:00+00:00"), DateTimeFormatter.ISO_DATE_TIME);
	    OffsetDateTime dateFim = OffsetDateTime.parse(dataFim.concat("T23:59:59+00:00"), DateTimeFormatter.ISO_DATE_TIME);

	    List<ConsumoPorMaquina> lista = (List<ConsumoPorMaquina>) cadastroSaida.filtrarConsumoPorMaquina(dateInicio, dateFim);

	    // Gerar o arquivo .xls usando a lista de consumo

	    InputStream consumo = new FileInputStream("C:\\imagensApi\\controleCombustivel\\arquivo_diesel\\consumo.xls");
		criarArquivoDiesel.criarPlanilha(lista);


	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel");
	    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=consumo.xls");

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(new InputStreamResource(consumo));
	}

	@GetMapping("/filtrar-consumo-glp")
	public List<?> filtrarConsumoGlp(@RequestParam String dataInicio,@RequestParam String dataFim){
		//	long combustivelId = Integer.valueOf(id);

		OffsetDateTime dateInicio = OffsetDateTime.parse(dataInicio.concat("T00:00:00+00:00"),DateTimeFormatter.ISO_DATE_TIME);//OffsetDateTime.of(date, ZoneOffset.UTC);
		OffsetDateTime dateFim = OffsetDateTime.parse(dataFim.concat("T23:59:59+00:00"),DateTimeFormatter.ISO_DATE_TIME);//OffsetDateTime.of(date, ZoneOffset.UTC);

		System.out.println(dateInicio);

		List<?> lista = cadastroSaida.filtrarConsumoGlp(dateInicio,dateFim);

		for(Object s : lista) {
			System.out.println(s);
		}

		return cadastroSaida.filtrarConsumoGlp(dateInicio, dateFim);
	}
	
	@GetMapping("/filtrar-consumo-maquina-agregado")
	public List<?> filtrarConsumoMaquinaAgregado(@RequestParam String dataInicio,@RequestParam String dataFim){
		//	long combustivelId = Integer.valueOf(id);

		OffsetDateTime dateInicio = OffsetDateTime.parse(dataInicio.concat("T00:00:00+00:00"),DateTimeFormatter.ISO_DATE_TIME);//OffsetDateTime.of(date, ZoneOffset.UTC);
		OffsetDateTime dateFim = OffsetDateTime.parse(dataFim.concat("T23:59:59+00:00"),DateTimeFormatter.ISO_DATE_TIME);//OffsetDateTime.of(date, ZoneOffset.UTC);

		System.out.println(dateInicio);

		List<?> lista = cadastroSaida.filtrarConsumoPorMaquinaAgregado(dateInicio,dateFim);

		for(Object s : lista) {
			System.out.println(s);
		}

		return cadastroSaida.filtrarConsumoPorMaquinaAgregado(dateInicio, dateFim);
	}

	@GetMapping("/filtrar-consumo-maquina")
	public List<?> filtrarConsumoMaquina(@RequestParam String dataInicio,@RequestParam String dataFim){
		//	long combustivelId = Integer.valueOf(id);

		OffsetDateTime dateInicio = OffsetDateTime.parse(dataInicio.concat("T00:00:00+00:00"),DateTimeFormatter.ISO_DATE_TIME);//OffsetDateTime.of(date, ZoneOffset.UTC);
		OffsetDateTime dateFim = OffsetDateTime.parse(dataFim.concat("T23:59:59+00:00"),DateTimeFormatter.ISO_DATE_TIME);//OffsetDateTime.of(date, ZoneOffset.UTC);

		System.out.println(dateInicio);

		List<?> lista = cadastroSaida.filtrarConsumoPorMaquina(dateInicio,dateFim);

		for(Object s : lista) {
			System.out.println(s);
		}

		return cadastroSaida.filtrarConsumoPorMaquina(dateInicio, dateFim);
	}
}
