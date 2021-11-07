package com.lduran.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.UsuarioModel;
import com.lduran.algafood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler
{
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * @param usuario
	 * @return
	 */
	public UsuarioModel toModel(Usuario usuario)
	{
		return modelMapper.map(usuario, UsuarioModel.class);
	}

	public List<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios)
	{
		return usuarios.stream().map(usuario -> toModel(usuario)).collect(Collectors.toList());
	}
}
