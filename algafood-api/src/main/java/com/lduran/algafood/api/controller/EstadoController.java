package com.lduran.algafood.api.controller;

import java.util.List;

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

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
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
	public ResponseEntity<Estado> buscar(@PathVariable long estadoId)
	{
		Estado estado = cadastroEstado.buscar(estadoId);

		if (estado != null)
		{
			return ResponseEntity.ok(estado);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Estado> adicionar(@RequestBody Estado estado)
	{
		return ResponseEntity.ok(cadastroEstado.salvar(estado));
	}

	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable long estadoId, @RequestBody Estado estado)
	{
		Estado estadoAtual = cadastroEstado.buscar(estadoId);

		if (estadoAtual != null)
		{
			estado.setId(estadoId);
			return ResponseEntity.ok(cadastroEstado.salvar(estado));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{estadoId}")
	public ResponseEntity<Estado> remover(@PathVariable long estadoId)
	{
		try
		{
			cadastroEstado.remover(estadoId);
			return ResponseEntity.noContent().build();
		}
		catch (EntidadeNaoEncontradaException e)
		{
			return ResponseEntity.notFound().build();
		}
		catch (EntidadeEmUsoException e)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
