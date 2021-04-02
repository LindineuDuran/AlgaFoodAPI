package com.lduran.algafoodapi.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.lduran.algafoodapi.injdep.notificacao.NivelUrgencia;
import com.lduran.algafoodapi.injdep.notificacao.Notificador;
import com.lduran.algafoodapi.injdep.notificacao.TipoDoNotificador;
import com.lduran.algafoodapi.injdep.service.ClienteAtivadoEvent;

@Component
public class NotificacaoService
{
	@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
	@Autowired
	private Notificador notificador;

	@EventListener
	public void clienteAtivadoListener(ClienteAtivadoEvent event)
	{
		notificador.notificar(event.getCliente(), "Seu cadastro no sistema está ativo");
	}
}
