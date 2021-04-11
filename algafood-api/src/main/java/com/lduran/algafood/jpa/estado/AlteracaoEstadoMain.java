package com.lduran.algafood.jpa.estado;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.lduran.algafood.AlgafoodApiApplication;
import com.lduran.algafood.domain.model.Estado;
import com.lduran.algafood.domain.repository.EstadoRepository;

public class AlteracaoEstadoMain
{
	public static void main(String[] args)
	{
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		EstadoRepository estados = applicationContext.getBean(EstadoRepository.class);

		Estado estado = new Estado();
		estado.setId(1L);
		estado.setNome("ES");

		estado = estados.adicionar(estado);

		System.out.printf("%d - %s\n", estado.getId(), estado.getNome());
	}

}
