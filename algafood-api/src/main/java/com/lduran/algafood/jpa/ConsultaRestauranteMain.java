package com.lduran.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.lduran.algafood.AlgafoodApiApplication;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.repository.RestauranteRepository;

public class ConsultaRestauranteMain
{
	public static void main(String[] args)
	{
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);

		List<Restaurante> todosRestaurantes = restaurantes.todos();

		todosRestaurantes.forEach(r ->
		{
			System.out.printf("%s - %f - %s\n", r.getNome(), r.getTaxaFrete(), r.getCozinha().getNome());
		});
	}

}
