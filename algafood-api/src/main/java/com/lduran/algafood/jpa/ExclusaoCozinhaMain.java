package com.lduran.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.lduran.algafood.AlgafoodApiApplication;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.repository.CozinhaRepository;

public class ExclusaoCozinhaMain
{
	public static void main(String[] args)
	{
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);

		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);

		cozinhas.remover(cozinha);

		System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome());
	}

}
