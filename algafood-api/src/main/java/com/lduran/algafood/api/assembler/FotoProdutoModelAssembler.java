package com.lduran.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.FotoProdutoModel;
import com.lduran.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler
{
	@Autowired
	private ModelMapper modelMapper;

	public FotoProdutoModel toModel(FotoProduto foto)
	{
		return modelMapper.map(foto, FotoProdutoModel.class);
	}

	public List<FotoProdutoModel> toCollectionModel(List<FotoProduto> fotos)
	{
		return fotos.parallelStream().map(foto -> toModel(foto)).collect(Collectors.toList());
	}
}
