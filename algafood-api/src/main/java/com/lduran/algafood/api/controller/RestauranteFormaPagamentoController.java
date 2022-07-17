package com.lduran.algafood.api.controller;

import java.util.List;

import com.lduran.algafood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.lduran.algafood.api.model.FormaPagamentoModel;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi
{
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private FormaPagamentoModelAssembler assembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FormaPagamentoModel>> listar(@PathVariable Long restauranteId)
	{
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
		return ResponseEntity.ok(assembler.toCollectionModel(restaurante.getFormasPagamento()));
	}

	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId)
	{
		cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
	}

	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId)
	{
		cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	}
}