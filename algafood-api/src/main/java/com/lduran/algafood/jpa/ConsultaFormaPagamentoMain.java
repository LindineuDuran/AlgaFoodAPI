package com.lduran.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.lduran.algafood.AlgafoodApiApplication;
import com.lduran.algafood.domain.model.FormaPagamento;
import com.lduran.algafood.domain.repository.FormaPagamentoRepository;

public class ConsultaFormaPagamentoMain
{

	public static void main(String[] args)
	{
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);

		List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.todas();

		for (FormaPagamento formaPagamento : todasFormasPagamentos)
		{
			System.out.println(formaPagamento.getDescricao());
		}
	}

}