package com.controle.combustivel.api.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controle.combustivel.api.exception.EntidadeNaoEncontradaException;
import com.controle.combustivel.domain.model.Maquina;
import com.controle.combustivel.domain.service.CadastroMaquinaService;
import com.controle.combustivel.domain.service.QrCodeService;
import com.google.zxing.WriterException;

@RestController
@RequestMapping(value = "/maquinas")
public class MaquinaController {

	@Autowired
	private CadastroMaquinaService cadastroMaquina;
	@Autowired
	private QrCodeService codeService;
	@PostMapping
	public Maquina salvar(@RequestBody Maquina maquina) throws IOException, WriterException{
		Long maquinaId = maquina.getCodigoSap();

		codeService.gerarQrCodeFile(maquinaId.toString());;
		return cadastroMaquina.salvar(maquina);
	}


	@GetMapping("/{codigoSap}")
	public Maquina buscar(@PathVariable long codigoSap) {
		return cadastroMaquina.buscar(codigoSap);
	}
	
	@GetMapping("/codigo/{codigoSap}")
	public ResponseEntity<InputStreamResource> retornarQr(@PathVariable long codigoSap) throws FileNotFoundException {
		//Path path = Paths.get("C:\\imagensApi\\controleCombustivel\\qr_code");
		InputStream qrcode = new FileInputStream("C:\\imagensApi\\controleCombustivel\\qr_code\\"+codigoSap+".png");
		
		 return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.body(new InputStreamResource(qrcode));	
	}
	@GetMapping
	public List<Maquina> listar(){
		return cadastroMaquina.listar();
	}
	@GetMapping("/ativos")
	public List<Maquina> listarAtivos(){
		return cadastroMaquina.listarAtivos();
	}
	@GetMapping("/inativos")
	public List<Maquina> listarInativos(){
		return cadastroMaquina.listarInativos();
	}

	@PutMapping(value = "/{codigoSap}")
	public Maquina atualizar (@RequestBody Maquina maquinaAtualizada, @PathVariable long codigoSap) {
		try {
			Maquina maquinaAtual = cadastroMaquina.buscar(codigoSap);
			BeanUtils.copyProperties(maquinaAtualizada, maquinaAtual, "codigoSap");
			return cadastroMaquina.salvar(maquinaAtual);
		}catch(EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(codigoSap);
		}

	}
	@PutMapping(value = "/desativar/{codigoSap}")
	public Maquina desativar (@PathVariable long codigoSap) {
		try {
			Maquina maquinaAtual = cadastroMaquina.buscar(codigoSap);
			maquinaAtual.setAtivo(false);
			return cadastroMaquina.salvar(maquinaAtual);
		}catch(EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(codigoSap);
		}
	}
	@PutMapping(value = "/ativar/{codigoSap}")
	public Maquina ativar (@PathVariable long codigoSap) {
		try {
			Maquina maquinaAtual = cadastroMaquina.buscar(codigoSap);
			maquinaAtual.setAtivo(true);
			return cadastroMaquina.salvar(maquinaAtual);
		}catch(EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(codigoSap);
		}

	}
}
