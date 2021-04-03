package com.lduran.algafoodapi.injdep.notificacao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lduran.algafoodapi.injdep.model.Cliente;

@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmail implements Notificador
{
	@Value("${notificador.email.host-servidor}")
	private String host;

	@Value("${notificador.email.porta-servidor}")
	private int porta;

	@Override
	public void notificar(Cliente cliente, String mensagem)
	{
		System.out.println("Host: " + host);
		System.out.println("Porta: " + porta);

		System.out.printf("Notificando %s através do e-mail %s: %s\n", cliente.getNome(), cliente.getEmail(), mensagem);
	}
}
