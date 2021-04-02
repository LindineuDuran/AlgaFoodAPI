package com.lduran.algafoodapi.injdep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafoodapi.injdep.model.Cliente;
import com.lduran.algafoodapi.injdep.notificacao.NivelUrgencia;
import com.lduran.algafoodapi.injdep.notificacao.Notificador;
import com.lduran.algafoodapi.injdep.notificacao.TipoDoNotificador;

@Component
public class AtivacaoClienteService
{
	@TipoDoNotificador(NivelUrgencia.URGENTE)
	@Autowired
	private Notificador notificador;

	public void ativar(Cliente cliente)
	{
		cliente.ativar();

		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}
}