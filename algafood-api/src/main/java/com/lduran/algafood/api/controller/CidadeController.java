package com.lduran.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

import com.lduran.algafood.api.assembler.CidadeInputModelDisassembler;
import com.lduran.algafood.api.assembler.CidadeModelAssembler;
import com.lduran.algafood.api.model.CidadeModel;
import com.lduran.algafood.api.model.input.CidadeInputModel;
import com.lduran.algafood.domain.exception.EstadoNaoEncontradoException;
import com.lduran.algafood.domain.exception.NegocioException;
import com.lduran.algafood.domain.model.Cidade;
import com.lduran.algafood.domain.service.CadastroCidadeService;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController
{
	@Autowired
	private CadastroCidadeService cadastroCidade;
	@Autowired
	private CidadeModelAssembler assembler;
	@Autowired
	private CidadeInputModelDisassembler disassembler;
	@GetMapping
	@ApiOperation("Listar as cidades")
	public ResponseEntity<List<CidadeModel>> listar()
	{
		return ResponseEntity.ok(assembler.toCollectionModel(cadastroCidade.listar()));
	}
	@GetMapping("/{cidadeId}")
	@ApiOperation("Buscar uma cidade por ID")
	public CidadeModel buscar(@ApiParam("ID de uma cidade") @PathVariable long cidadeId)
	{
		return assembler.toModel(cadastroCidade.buscar(cidadeId));
	}
	@PostMapping
	@ApiOperation("Adicionar uma nova cidade")
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInputModel cidadeInput)
	{
		try
		{
			Cidade cidade = disassembler.toDomainObject(cidadeInput);
			return assembler.toModel(cadastroCidade.salvar(cidade));
		}
		catch (EstadoNaoEncontradoException e)
		{
			throw new NegocioException(e.getMessage(), e);
		}
	}
	@PutMapping("/{cidadeId}")
	@ApiOperation("Atualizar uma cidade por ID")
	public CidadeModel atualizar(@ApiParam("ID de uma cidade") @PathVariable long cidadeId,
			                     @RequestBody @Valid CidadeInputModel cidadeInput)
	{
		try
		{
			Cidade cidadeAtual = cadastroCidade.buscar(cidadeId);

			disassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			return assembler.toModel(cadastroCidade.salvar(cidadeAtual));
		}
		catch (EstadoNaoEncontradoException e)
		{
			throw new NegocioException(e.getMessage(), e);
		}
	}
	@DeleteMapping("/{cidadeId}")
	@ApiOperation("Remover uma cidade por ID")
	public void remover(@ApiParam("ID de uma cidade") @PathVariable long cidadeId)
	{
		cadastroCidade.remover(cidadeId);
	}
}
