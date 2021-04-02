package com.lduran.algafoodapi.injdep.notificacao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.lduran.algafoodapi.injdep.model.Cliente;

@Qualifier("normal")
@Component
public class NotificadorEmail implements Notificador
{
	@Override
	public void notificar(Cliente cliente, String mensagem)
	{
		System.out.printf("Notificando %s através do e-mail %s: %s\n", cliente.getNome(), cliente.getEmail(), mensagem);
	}
}
