package com.lduran.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException
{

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String codigPedidoo)
	{
		super(String.format("Não existe um pedido com código %s", codigPedidoo));
	}
}