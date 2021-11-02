package com.lduran.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.input.RestauranteInputModel;
import com.lduran.algafood.domain.model.Cidade;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputModelDisassembler
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

		// Para evitar: Caused by: org.hibernate.HibernateException: identifier of an
		// instance of com.lduran.algafood.domain.model.Cidade was altered from 2 to 1
		if (restaurante.getEndereco() != null)
		{
			restaurante.getEndereco().setCidade(new Cidade());
		}

		modelMapper.map(restauranteInputModel, restaurante);
	}
}
