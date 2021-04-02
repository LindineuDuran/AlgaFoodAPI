package com.lduran.algafoodapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lduran.algafoodapi.injdep.notificacao.NotificadorEmail;

@Configuration
public class NotificacaoConfig
{
	@Bean
	public NotificadorEmail notificadorEmail()
	{
		var notificador = new NotificadorEmail("smtp.algaworks.com.br");
		notificador.setCaixaAlta(true);

		return notificador;
	}
}
