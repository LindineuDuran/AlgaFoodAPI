package com.lduran.algafoodapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lduran.algafoodapi.injdep.notificacao.Notificador;
import com.lduran.algafoodapi.injdep.service.AtivacaoClienteService;

@Configuration
public class ServiceConfig
{
	@Bean
	public AtivacaoClienteService ativacaoClienteService(Notificador notificador)
	{
		return new AtivacaoClienteService(notificador);
	}
}
