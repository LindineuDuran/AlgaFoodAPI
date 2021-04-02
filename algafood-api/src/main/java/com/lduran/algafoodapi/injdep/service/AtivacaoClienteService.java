package com.lduran.algafoodapi.injdep.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.lduran.algafoodapi.injdep.model.Cliente;
import com.lduran.algafoodapi.injdep.notificacao.NivelUrgencia;
import com.lduran.algafoodapi.injdep.notificacao.Notificador;
import com.lduran.algafoodapi.injdep.notificacao.TipoDoNotificador;

//@Component
public class AtivacaoClienteService
{
	@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
	@Autowired
	private Notificador notificador;

//	@PostConstruct
	public void init()
	{
		System.out.println("INIT");
	}

//	@PreDestroy
	public void destroy()
	{
		System.out.println("DESTROY");
	}

	public void ativar(Cliente cliente)
	{
		cliente.ativar();

		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}
}