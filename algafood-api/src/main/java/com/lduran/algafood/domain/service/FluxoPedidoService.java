package com.lduran.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService
{
	@Autowired
	private EmissaoPedidoService emissaoPedidoService;

	@Transactional
	public void confirmar(String codigoPedido)
	{
		Pedido pedido = emissaoPedidoService.buscar(codigoPedido);

		pedido.confirmar();

	}

	@Transactional
	public void cancelar(String codigoPedido)
	{
		Pedido pedido = emissaoPedidoService.buscar(codigoPedido);

		pedido.cancelar();
	}

	@Transactional
	public void entregar(String codigoPedido)
	{
		Pedido pedido = emissaoPedidoService.buscar(codigoPedido);

		pedido.entregar();

	}
}
