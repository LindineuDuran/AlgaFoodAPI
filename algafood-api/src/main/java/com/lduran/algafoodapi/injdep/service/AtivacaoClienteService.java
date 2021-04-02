package com.lduran.algafoodapi.injdep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.lduran.algafoodapi.injdep.model.Cliente;

@Component
public class AtivacaoClienteService
{
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	public void ativar(Cliente cliente)
	{
		cliente.ativar();

		eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
	}
}