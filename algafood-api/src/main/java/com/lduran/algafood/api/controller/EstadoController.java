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

import com.lduran.algafood.api.assembler.EstadoInputModelDisassembler;
import com.lduran.algafood.api.assembler.EstadoModelAssembler;
import com.lduran.algafood.api.model.EstadoModel;
import com.lduran.algafood.api.model.input.EstadoInputModel;
import com.lduran.algafood.domain.model.Estado;
import com.lduran.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController
{
	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoModelAssembler assembler;

	@Autowired
	private EstadoInputModelDisassembler disassembler;

	@GetMapping
	public ResponseEntity<List<EstadoModel>> listar()
	{
		List<Estado> estados = cadastroEstado.listar();
		return ResponseEntity.ok(assembler.toCollectionModel(estados));
	}

	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable long estadoId)
	{
		Estado estado = cadastroEstado.buscar(estadoId);
		return assembler.toModel(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<EstadoModel> adicionar(@RequestBody @Valid EstadoInputModel estadoInput)
	{
		Estado estado = disassembler.toDomainObject(estadoInput);
		return ResponseEntity.ok(assembler.toModel(cadastroEstado.salvar(estado)));
	}

	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable long estadoId, @RequestBody @Valid EstadoInputModel estadoInput)
	{
		Estado estadoAtual = cadastroEstado.buscar(estadoId);

		disassembler.copyToDomainObject(estadoInput, estadoAtual);

		return assembler.toModel(cadastroEstado.salvar(estadoAtual));
	}

	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable long estadoId)
	{
		cadastroEstado.remover(estadoId);
	}
}
