package com.lduran.algafood.jpa;

import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.lduran.algafood.AlgafoodApiApplication;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.repository.RestauranteRepository;

public class InclusaoRestauranteMain
{
	public static void main(String[] args)
	{
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);

		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Cantina da Nena");
		restaurante1.setTaxaFrete(new BigDecimal(9.5));

		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Restaurante do Sujinho");
		restaurante2.setTaxaFrete(new BigDecimal(7.5));

		restaurante1 = restaurantes.adicionar(restaurante1);
		restaurante2 = restaurantes.adicionar(restaurante2);

		System.out.printf("%d - %s\n", restaurante1.getId(), restaurante1.getNome());
		System.out.printf("%d - %s\n", restaurante2.getId(), restaurante2.getNome());
	}

}
