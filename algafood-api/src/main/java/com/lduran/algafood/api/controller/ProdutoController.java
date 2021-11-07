package com.lduran.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class ProdutoController
{
	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private ProdutoModelAssembler assembler;

	@Autowired
	private ProdutoInputModelDisassembler disassembler;

	@GetMapping
	public ResponseEntity<List<ProdutoModel>> listar(@PathVariable Long restauranteId)
	{
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);

		List<Produto> todosProdutos = cadastroProduto.listar(restaurante);

		return ResponseEntity.ok(assembler.toCollectionModel(todosProdutos));
	}

	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId)
	{
		return assembler.toModel(cadastroProduto.buscar(restauranteId, produtoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputModel produtoInput)
	{
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);

		Produto produto = disassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);

		produto = cadastroProduto.salvar(produto);

		return assembler.toModel(produto);
	}

	@PutMapping("/{produtoId}")
	public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInputModel produtoInput)
	{
		Produto produtoAtual = cadastroProduto.buscar(restauranteId, produtoId);

		disassembler.copyToDomainObject(produtoInput, produtoAtual);

		return assembler.toModel(cadastroProduto.salvar(produtoAtual));
	}
}
