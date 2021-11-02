package com.lduran.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.input.GrupoInputModel;
import com.lduran.algafood.domain.model.Grupo;

@Component
public class GrupoInputModelDisassembler
{
	@Autowired
	private ModelMapper modelMapper;

	public Grupo toDomainObject(GrupoInputModel grupoInput)
	{
		return modelMapper.map(grupoInput, Grupo.class);
	}

	public void copyToDomainObject(GrupoInputModel grupoInput, Grupo grupo)
	{
		modelMapper.map(grupoInput, grupo);
	}
}
