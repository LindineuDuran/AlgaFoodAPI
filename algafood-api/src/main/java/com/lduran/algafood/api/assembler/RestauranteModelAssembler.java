package com.lduran.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.RestauranteModel;
import com.lduran.algafood.api.model.input.RestauranteInputModel;
import com.lduran.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler
{
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * @param restaurante
	 * @return
	 */
	public RestauranteModel toModel(Restaurante restaurante)
	{
		return modelMapper.map(restaurante, RestauranteModel.class);
	}

	/**
	 * @param restaurante
	 * @return
	 */
	public RestauranteInputModel toInputModel(Restaurante restaurante)
	{
		return modelMapper.map(restaurante, RestauranteInputModel.class);
	}

	public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes)
	{
		return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
	}
}
