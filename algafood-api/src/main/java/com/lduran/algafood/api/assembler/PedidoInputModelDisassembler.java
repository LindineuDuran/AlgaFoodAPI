package com.lduran.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.input.PedidoInputModel;
import com.lduran.algafood.domain.model.Pedido;

@Component
public class PedidoInputModelDisassembler
{
	@Autowired
	private ModelMapper modelMapper;

	public Pedido toDomainObject(PedidoInputModel pedidoInput)
	{
		return modelMapper.map(pedidoInput, Pedido.class);
	}

	public void copyToDomainObject(PedidoInputModel pedidoInput, Pedido pedido)
	{
		modelMapper.map(pedidoInput, pedido);
	}
}
