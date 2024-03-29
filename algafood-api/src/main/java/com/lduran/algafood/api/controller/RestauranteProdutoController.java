package com.lduran.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.lduran.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.algafood.api.assembler.ProdutoInputModelDisassembler;
import com.lduran.algafood.api.assembler.ProdutoModelAssembler;
import com.lduran.algafood.api.model.ProdutoModel;
import com.lduran.algafood.api.model.input.ProdutoInputModel;
import com.lduran.algafood.domain.model.Produto;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.service.CadastroProdutoService;
import com.lduran.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi
{
	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private ProdutoModelAssembler assembler;

	@Autowired
	private ProdutoInputModelDisassembler disassembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProdutoModel>> listar(@PathVariable Long restauranteId,
			@RequestParam(required = false) boolean incluirInativos)
	{
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);

		List<Produto> todosProdutos = null;
		if (incluirInativos)
		{
			todosProdutos = cadastroProduto.listar(restaurante);
		}
		else
		{
			todosProdutos = cadastroProduto.listarAtivos(restaurante);
		}

		return ResponseEntity.ok(assembler.toCollectionModel(todosProdutos));
	}

	@GetMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId)
	{
		return assembler.toModel(cadastroProduto.buscar(restauranteId, produtoId));
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputModel produtoInput)
	{
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);

		Produto produto = disassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);

		produto = cadastroProduto.salvar(produto);

		return assembler.toModel(produto);
	}

	@PutMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInputModel produtoInput)
	{
		Produto produtoAtual = cadastroProduto.buscar(restauranteId, produtoId);

		disassembler.copyToDomainObject(produtoInput, produtoAtual);

		return assembler.toModel(cadastroProduto.salvar(produtoAtual));
	}
}
