package com.lduran.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lduran.algafood.api.model.RestauranteModel;
import com.lduran.algafood.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig
{
	@Bean
	public ModelMapper modelMapper()
	{
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class).addMapping(Restaurante::getTaxaFrete,
				RestauranteModel::setPrecoFrete);

		return modelMapper;
	}
}
