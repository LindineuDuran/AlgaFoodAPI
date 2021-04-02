package com.lduran.algafoodapi.injdep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafoodapi.injdep.model.Cliente;
import com.lduran.algafoodapi.injdep.notificacao.Notificador;

@Component
public class AtivacaoClienteService
{
	@Autowired
	private List<Notificador> notificadores;

	public void ativar(Cliente cliente)
	{
		cliente.ativar();

		notificadores.forEach(n ->
		{
			n.notificar(cliente, "Seu cadastro no sistema está ativo!");
		});
	}
}