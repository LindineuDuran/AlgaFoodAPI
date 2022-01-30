package com.lduran.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.model.Pedido;
import com.lduran.algafood.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService
{
	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Transactional
	public void confirmar(String codigoPedido)
	{
		Pedido pedido = emissaoPedido.buscar(codigoPedido);
		pedido.confirmar();

		pedidoRepository.save(pedido);
	}

	@Transactional
	public void cancelar(String codigoPedido)
	{
		Pedido pedido = emissaoPedido.buscar(codigoPedido);
		pedido.cancelar();

		pedidoRepository.save(pedido);
	}

	@Transactional
	public void entregar(String codigoPedido)
	{
		Pedido pedido = emissaoPedido.buscar(codigoPedido);

		pedido.entregar();

	}
}
