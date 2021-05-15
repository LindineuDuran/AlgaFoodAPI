package com.lduran.algafood.jpa.restaurante;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.lduran.algafood.AlgafoodApiApplication;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.repository.RestauranteRepository;

public class AlteracaoRestauranteMain
{
	public static void main(String[] args)
	{
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);

		Restaurante restaurante = new Restaurante();
		restaurante.setId(1L);
		restaurante.setNome("Cantina da Nena");

		restaurante = restaurantes.salvar(restaurante);

		System.out.printf("%d - %s\n", restaurante.getId(), restaurante.getNome());
	}

}
