package com.lduran.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.input.CidadeInputModel;
import com.lduran.algafood.domain.model.Cidade;
import com.lduran.algafood.domain.model.Estado;

@Component
public class CidadeInputModelDisassembler
{
	@Autowired
	private ModelMapper modelMapper;

	public Cidade toDomainObject(CidadeInputModel cidadeInput)
	{
		return modelMapper.map(cidadeInput, Cidade.class);
	}

	public void copyToDomainObject(CidadeInputModel cidadeInput, Cidade cidade)
	{
		// Para evitar: Caused by: org.hibernate.HibernateException: identifier of an
		// instance of com.lduran.algafood.domain.model.Estado was altered from 3 to 5
		cidade.setEstado(new Estado());

		modelMapper.map(cidadeInput, cidade);
	}
}
