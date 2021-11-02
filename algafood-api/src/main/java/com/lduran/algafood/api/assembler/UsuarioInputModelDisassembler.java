package com.lduran.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.input.UsuarioInputModel;
import com.lduran.algafood.domain.model.Usuario;

@Component
public class UsuarioInputModelDisassembler
{
	@Autowired
	private ModelMapper modelMapper;

	public Usuario toDomainObject(UsuarioInputModel usuarioInput)
	{
		return modelMapper.map(usuarioInput, Usuario.class);
	}

	public void copyToDomainObject(UsuarioInputModel usuarioInputModel, Usuario usuario)
	{
		modelMapper.map(usuarioInputModel, usuario);
	}
}
