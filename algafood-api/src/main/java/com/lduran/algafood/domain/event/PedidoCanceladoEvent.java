package com.lduran.algafood.domain.event;

import com.lduran.algafood.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent
{
	private Pedido pedido;
}
