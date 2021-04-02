package com.lduran.algafoodapi.injdep.notificacao;

import org.springframework.stereotype.Component;

import com.lduran.algafoodapi.injdep.model.Cliente;

@Component
public class NotificadorEmail implements Notificador
{
	public NotificadorEmail()
	{
		System.out.println("NotificadorEmail");
	}

	@Override
	public void notificar(Cliente cliente, String mensagem)
	{
		System.out.printf("Notificando %s através do e-mail %s: %s\n", cliente.getNome(), cliente.getEmail(), mensagem);
	}
}
