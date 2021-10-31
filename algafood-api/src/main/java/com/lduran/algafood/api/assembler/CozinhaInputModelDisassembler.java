package com.lduran.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.input.CozinhaInputModel;
import com.lduran.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputModelDisassembler
{
	@Autowired
	private ModelMapper modelMapper;

	public Cozinha toDomainObject(CozinhaInputModel cozinhaInput)
	{
		return modelMapper.map(cozinhaInput, Cozinha.class);
	}

	public void copyToDomainObject(CozinhaInputModel cozinhaInput, Cozinha cozinha)
	{
		modelMapper.map(cozinhaInput, cozinha);
	}
}
