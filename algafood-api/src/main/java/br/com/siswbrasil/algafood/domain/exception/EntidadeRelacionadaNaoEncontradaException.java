package br.com.siswbrasil.algafood.domain.exception;

public class EntidadeRelacionadaNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadeRelacionadaNaoEncontradaException(String message) {
		super(message);
	}

}
