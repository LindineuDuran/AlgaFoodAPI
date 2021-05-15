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
import org.springframework.web.bind.annotation.RestController;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController
{
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@GetMapping
	public ResponseEntity<List<Restaurante>> listar()
	{
		return ResponseEntity.ok(cadastroRestaurante.listar());
	}

	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable long restauranteId)
	{
		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);

		if (restaurante != null)
		{
			return ResponseEntity.ok(restaurante);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante)
	{
		try
		{
			restaurante = cadastroRestaurante.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		}
		catch (EntidadeNaoEncontradaException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable long restauranteId, @RequestBody Restaurante restaurante)
	{
		try
		{
			Restaurante restauranteAtual = cadastroRestaurante.buscar(restauranteId);

			if (restauranteAtual != null)
			{
				restaurante.setId(restauranteId);
				return ResponseEntity.ok(cadastroRestaurante.salvar(restaurante));
			}

			return ResponseEntity.notFound().build();
		}
		catch (EntidadeNaoEncontradaException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable long restauranteId)
	{
		try
		{
			cadastroRestaurante.remover(restauranteId);
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