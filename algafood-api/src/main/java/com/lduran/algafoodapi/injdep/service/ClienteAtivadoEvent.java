package com.lduran.algafoodapi.injdep.service;

import com.lduran.algafoodapi.injdep.model.Cliente;

public class ClienteAtivadoEvent
{
	private Cliente cliente;

	public ClienteAtivadoEvent(Cliente cliente)
	{
		super();
		this.cliente = cliente;
	}

	public Cliente getCliente()
	{
		return cliente;
	}
}
