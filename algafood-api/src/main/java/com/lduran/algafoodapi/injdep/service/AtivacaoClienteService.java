package com.lduran.algafoodapi.injdep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafoodapi.injdep.model.Cliente;
import com.lduran.algafoodapi.injdep.notificacao.Notificador;

@Component
public class AtivacaoClienteService
{
	@Autowired(required = false)
	private Notificador notificador;

	public void ativar(Cliente cliente)
	{
		cliente.ativar();

		if (notificador != null)
		{
			this.notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
		}
		else
		{
			System.out.println("Não existe notificador, mas cliente foi ativado");
		}
	}
}