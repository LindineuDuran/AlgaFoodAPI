package com.lduran.algafood.jpa.estado;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.lduran.algafood.AlgafoodApiApplication;
import com.lduran.algafood.domain.model.Estado;
import com.lduran.algafood.domain.repository.EstadoRepository;

public class InclusaoEstadoMain
{
	public static void main(String[] args)
	{
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		EstadoRepository estados = applicationContext.getBean(EstadoRepository.class);

		Estado estado1 = new Estado();
		estado1.setNome("ES");

		Estado estado2 = new Estado();
		estado2.setNome("CE");

		estado1 = estados.adicionar(estado1);
		estado2 = estados.adicionar(estado2);

		System.out.printf("%d - %s\n", estado1.getId(), estado1.getNome());
		System.out.printf("%d - %s\n", estado2.getId(), estado2.getNome());
	}

}
