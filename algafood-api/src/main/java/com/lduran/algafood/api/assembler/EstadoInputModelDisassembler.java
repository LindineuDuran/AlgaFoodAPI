package com.lduran.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.input.EstadoInputModel;
import com.lduran.algafood.domain.model.Estado;

@Component
public class EstadoInputModelDisassembler
{
	@Autowired
	private ModelMapper modelMapper;

	public Estado toDomainObject(EstadoInputModel estadoInput)
	{
		return modelMapper.map(estadoInput, Estado.class);
	}

	public void copyToDomainObject(EstadoInputModel estadoInputModel, Estado estado)
	{
		modelMapper.map(estadoInputModel, estado);
	}
}
