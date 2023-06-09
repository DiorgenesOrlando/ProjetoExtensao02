package com.controle.combustivel.api.exception;

public class EntidadeNaoEncontradaException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	public EntidadeNaoEncontradaException(long id) {
		this(String.format("Entidade não encontrada com codigo %d", id));
	}
	
}
