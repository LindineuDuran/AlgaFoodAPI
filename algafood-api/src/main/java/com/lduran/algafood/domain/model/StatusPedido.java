package com.lduran.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido
{
	CRIADO("Criado"), CONFIRMADO("Confirmado", CRIADO), ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);

	private final String descricao;
	private final List<StatusPedido> statusAnteriores;

	/**
	 * @param descricao
	 */
	StatusPedido(String descricao, StatusPedido... statusAnteriores)
	{
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}

	public String getDescricao()
	{
		return this.descricao;
	}

	public boolean naoPodeAlterarPara(StatusPedido novoStatus)
	{
		return !novoStatus.statusAnteriores.contains(this);
	}
}
