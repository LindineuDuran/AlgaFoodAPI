package com.lduran.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.input.RestauranteInputModel;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputModelDisAssembler
{
	public Restaurante toDomainObject(RestauranteInputModel restauranteInput)
	{
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());

		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		restaurante.setCozinha(cozinha);

		return restaurante;
	}
}
