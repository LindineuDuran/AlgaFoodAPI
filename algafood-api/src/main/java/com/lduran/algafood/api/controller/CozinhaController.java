package com.lduran.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.algafood.api.assembler.CozinhaInputModelDisassembler;
import com.lduran.algafood.api.assembler.CozinhaModelAssembler;
import com.lduran.algafood.api.model.CozinhaModel;
import com.lduran.algafood.api.model.input.CozinhaInputModel;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController
{
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CozinhaModelAssembler assembler;

	@Autowired
	private CozinhaInputModelDisassembler disassembler;

	@GetMapping
	public ResponseEntity<List<CozinhaModel>> listar()
	{
		return ResponseEntity.ok(assembler.toCollectionModel(cadastroCozinha.listar()));
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable long cozinhaId)
	{
		return assembler.toModel(cadastroCozinha.buscar(cozinhaId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInputModel cozinhaInput)
	{
		Cozinha cozinha = disassembler.toDomainObject(cozinhaInput);
		return assembler.toModel(cadastroCozinha.salvar(cozinha));
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable long cozinhaId, @RequestBody @Valid CozinhaInputModel cozinhaInput)
	{
		Cozinha cozinhaAtual = cadastroCozinha.buscar(cozinhaId);

		disassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		return assembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable long cozinhaId)
	{
		cadastroCozinha.remover(cozinhaId);
	}
}
