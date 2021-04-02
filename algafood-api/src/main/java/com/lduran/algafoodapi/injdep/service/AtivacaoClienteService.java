package com.lduran.algafoodapi.injdep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafoodapi.injdep.model.Cliente;
import com.lduran.algafoodapi.injdep.notificacao.Notificador;

@Component
public class AtivacaoClienteService
{
	@Autowired
	private Notificador notificador;

	public void ativar(Cliente cliente)
	{
		cliente.ativar();

		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}
}