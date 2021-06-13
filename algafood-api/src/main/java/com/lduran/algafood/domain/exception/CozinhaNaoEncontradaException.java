package com.lduran.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException
{
	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String mensagem)
	{
		super(mensagem);
	}

	public CozinhaNaoEncontradaException(Long estadoId)
	{
		this(String.format("Não existe um cadastro de cozinha de código %d", estadoId));
	}
}
