package com.lduran.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.input.RestauranteInputModel;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputModelDisAssembler
{
	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInputModel restauranteInput)
	{
		return modelMapper.map(restauranteInput, Restaurante.class);
	}

	public void copyToDomainObject(RestauranteInputModel restauranteInputModel, Restaurante restaurante)
	{
		// Para evitar: Caused by: org.hibernate.HibernateException: identifier of an
		// instance of com.lduran.algafood.domain.model.Cozinha was altered from 3 to 5
		restaurante.setCozinha(new Cozinha());

		modelMapper.map(restauranteInputModel, restaurante);
	}
}
