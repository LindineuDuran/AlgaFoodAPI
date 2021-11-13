package com.lduran.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.algafood.api.assembler.PedidoModelAssembler;
import com.lduran.algafood.api.model.PedidoModel;
import com.lduran.algafood.domain.model.Pedido;
import com.lduran.algafood.domain.service.CadastroRestauranteService;
import com.lduran.algafood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController
{
	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private PedidoModelAssembler assembler;

	@GetMapping
	public ResponseEntity<List<PedidoModel>> listar()
	{
		List<Pedido> pedidos = emissaoPedido.listar();

		return ResponseEntity.ok(assembler.toCollectionModel(pedidos));
	}

	@GetMapping("/{pedidoId}")
	public PedidoModel buscar(@PathVariable Long pedidoId)
	{
		return assembler.toModel(emissaoPedido.buscar(pedidoId));
	}
}
