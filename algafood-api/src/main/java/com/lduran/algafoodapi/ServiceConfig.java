package com.lduran.algafoodapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lduran.algafoodapi.injdep.service.AtivacaoClienteService;

@Configuration
public class ServiceConfig
{
	@Bean(initMethod = "init", destroyMethod = "destroy")
	public AtivacaoClienteService ativacaoClienteService()
	{
		return new AtivacaoClienteService();
	}
}
