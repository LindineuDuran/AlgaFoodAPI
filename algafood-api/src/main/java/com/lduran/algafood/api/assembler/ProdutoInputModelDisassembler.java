package com.lduran.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.input.ProdutoInputModel;
import com.lduran.algafood.domain.model.Produto;

@Component
public class ProdutoInputModelDisassembler
{
	@Autowired
	private ModelMapper modelMapper;

	public Produto toDomainObject(ProdutoInputModel produtoInput)
	{
		return modelMapper.map(produtoInput, Produto.class);
	}

	public void copyToDomainObject(ProdutoInputModel produtoInput, Produto produto)
	{
		modelMapper.map(produtoInput, produto);
	}
}
