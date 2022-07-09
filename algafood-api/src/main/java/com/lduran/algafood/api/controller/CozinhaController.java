package com.lduran.algafood.api.controller;

import com.lduran.algafood.api.assembler.CozinhaInputModelDisassembler;
import com.lduran.algafood.api.assembler.CozinhaModelAssembler;
import com.lduran.algafood.api.model.CozinhaModel;
import com.lduran.algafood.api.model.input.CozinhaInputModel;
import com.lduran.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi
{
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CozinhaModelAssembler assembler;

	@Autowired
	private CozinhaInputModelDisassembler disassembler;

	@GetMapping("/todas")
	public ResponseEntity<List<CozinhaModel>> listar()
	{
		return ResponseEntity.ok(assembler.toCollectionModel(cadastroCozinha.listar()));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<CozinhaModel> listarPage(Pageable pageable)
	{
		Page<Cozinha> cozinhasPage = cadastroCozinha.listarPage(pageable);

		List<CozinhaModel> cozinhasModel = assembler.toCollectionModel(cozinhasPage.getContent());

		Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());

		return cozinhasModelPage;
	}

	@GetMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaModel buscar(@PathVariable long cozinhaId)
	{
		return assembler.toModel(cadastroCozinha.buscar(cozinhaId));
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInputModel cozinhaInput)
	{
		Cozinha cozinha = disassembler.toDomainObject(cozinhaInput);
		return assembler.toModel(cadastroCozinha.salvar(cozinha));
	}

	@PutMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaModel atualizar(@PathVariable long cozinhaId, @RequestBody @Valid CozinhaInputModel cozinhaInput)
	{
		Cozinha cozinhaAtual = cadastroCozinha.buscar(cozinhaId);

		disassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		return assembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
	}

	@DeleteMapping(value = "/{cozinhaId}", produces = {})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable long cozinhaId)
	{
		cadastroCozinha.remover(cozinhaId);
	}
}
