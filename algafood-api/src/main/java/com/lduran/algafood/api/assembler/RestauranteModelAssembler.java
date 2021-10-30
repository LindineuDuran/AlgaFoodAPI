package com.lduran.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.CozinhaModel;
import com.lduran.algafood.api.model.RestauranteModel;
import com.lduran.algafood.api.model.input.CozinhaIdInput;
import com.lduran.algafood.api.model.input.RestauranteInputModel;
import com.lduran.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler
{
	/**
	 * @param restaurante
	 * @return
	 */
	public RestauranteModel toModel(Restaurante restaurante)
	{
		CozinhaModel cozinhaModel = new CozinhaModel();
		cozinhaModel.setId(restaurante.getCozinha().getId());
		cozinhaModel.setNome(restaurante.getCozinha().getNome());

		RestauranteModel restauranteModel = new RestauranteModel();
		restauranteModel.setId(restaurante.getId());
		restauranteModel.setNome(restaurante.getNome());
		restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteModel.setCozinha(cozinhaModel);

		return restauranteModel;
	}

	/**
	 * @param restaurante
	 * @return
	 */
	public RestauranteInputModel toInputModel(Restaurante restaurante)
	{
		CozinhaIdInput cozinhaIdInput = new CozinhaIdInput();
		cozinhaIdInput.setId(restaurante.getCozinha().getId());

		RestauranteInputModel restauranteInputModel = new RestauranteInputModel();
		restauranteInputModel.setNome(restaurante.getNome());
		restauranteInputModel.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteInputModel.setCozinha(cozinhaIdInput);

		return restauranteInputModel;
	}

	public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes)
	{
		return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
	}
}
