package com.lduran.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lduran.algafood.api.model.PedidoResumoModel;
import com.lduran.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler
{
	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoModel toModel(Pedido pedido)
	{
		return modelMapper.map(pedido, PedidoResumoModel.class);
	}

	public List<PedidoResumoModel> toCollectionModel(List<Pedido> pedidos)
	{
		return pedidos.parallelStream().map(pedido -> toModel(pedido)).collect(Collectors.toList());
	}
}
