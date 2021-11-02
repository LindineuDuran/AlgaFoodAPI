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

import com.lduran.algafood.api.assembler.GrupoInputModelDisassembler;
import com.lduran.algafood.api.assembler.GrupoModelAssembler;
import com.lduran.algafood.api.model.GrupoModel;
import com.lduran.algafood.api.model.input.GrupoInputModel;
import com.lduran.algafood.domain.model.Grupo;
import com.lduran.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController
{
	@Autowired
	private CadastroGrupoService cadastroGrupo;

	@Autowired
	private GrupoModelAssembler assembler;

	@Autowired
	private GrupoInputModelDisassembler disassembler;

	@GetMapping
	public ResponseEntity<List<GrupoModel>> listar()
	{
		return ResponseEntity.ok(assembler.toCollectionModel(cadastroGrupo.listar()));
	}

	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable long grupoId)
	{
		return assembler.toModel(cadastroGrupo.buscar(grupoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInputModel grupoInput)
	{
		Grupo grupo = disassembler.toDomainObject(grupoInput);
		return assembler.toModel(cadastroGrupo.salvar(grupo));
	}

	@PutMapping("/{grupoId}")
	public GrupoModel atualizar(@PathVariable long grupoId, @RequestBody @Valid GrupoInputModel grupoInput)
	{
		Grupo grupoAtual = cadastroGrupo.buscar(grupoId);

		disassembler.copyToDomainObject(grupoInput, grupoAtual);

		return assembler.toModel(cadastroGrupo.salvar(grupoAtual));
	}

	@DeleteMapping("/{grupoId}")
	public void remover(@PathVariable long grupoId)
	{
		cadastroGrupo.remover(grupoId);
	}
}
