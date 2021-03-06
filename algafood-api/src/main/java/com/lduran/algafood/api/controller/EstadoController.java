package com.lduran.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.lduran.algafood.domain.model.Estado;
import com.lduran.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController
{
	@Autowired
	private CadastroEstadoService cadastroEstado;

	@GetMapping
	public ResponseEntity<List<Estado>> listar()
	{
		return ResponseEntity.ok(cadastroEstado.listar());
	}

	@GetMapping("/{estadoId}")
	public Estado buscar(@PathVariable long estadoId)
	{
		return cadastroEstado.buscar(estadoId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Estado> adicionar(@RequestBody @Valid Estado estado)
	{
		return ResponseEntity.ok(cadastroEstado.salvar(estado));
	}

	@PutMapping("/{estadoId}")
	public Estado atualizar(@PathVariable long estadoId, @RequestBody @Valid Estado estado)
	{
		Estado estadoAtual = cadastroEstado.buscar(estadoId);

		BeanUtils.copyProperties(estado, estadoAtual, "id");

		return cadastroEstado.salvar(estadoAtual);
	}

	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable long estadoId)
	{
		cadastroEstado.remover(estadoId);
	}
}
