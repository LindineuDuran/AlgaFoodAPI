package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lduran.algafood.domain.exception.PedidoNaoEncontradoException;
import com.lduran.algafood.domain.model.Pedido;
import com.lduran.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService
{
	@Autowired
	private PedidoRepository pedidoRepository;

	public List<Pedido> listar()
	{
		return pedidoRepository.findAll();
	}

	public Pedido buscar(Long pedidoId)
	{
		return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}
}
