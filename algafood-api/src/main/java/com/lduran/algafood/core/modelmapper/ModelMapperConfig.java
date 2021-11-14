package com.lduran.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lduran.algafood.api.model.EnderecoModel;
import com.lduran.algafood.api.model.input.ItemPedidoInputModel;
import com.lduran.algafood.domain.model.Endereco;
import com.lduran.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig
{
	@Bean
	public ModelMapper modelMapper()
	{
		var modelMapper = new ModelMapper();

		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		enderecoToEnderecoModelTypeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

		modelMapper.createTypeMap(ItemPedidoInputModel.class, ItemPedido.class)
				.addMappings(mapper -> mapper.skip(ItemPedido::setId));

		return modelMapper;
	}
}
