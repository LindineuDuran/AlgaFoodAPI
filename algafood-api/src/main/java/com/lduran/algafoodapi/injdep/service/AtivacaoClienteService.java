package com.lduran.algafoodapi.injdep.service;

import org.springframework.stereotype.Component;

import com.lduran.algafoodapi.injdep.model.Cliente;
import com.lduran.algafoodapi.injdep.notificacao.Notificador;

@Component
public class AtivacaoClienteService
{
	private Notificador notificador;

	/**
	 * @param notificador
	 */
	public AtivacaoClienteService(Notificador notificador)
	{
		this.notificador = notificador;

		System.out.println("AtivacaoClienteService: " + notificador);
	}

	public void ativar(Cliente cliente)
	{
		cliente.ativar();

		this.notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}
}