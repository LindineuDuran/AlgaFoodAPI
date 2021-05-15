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
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController
{
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@GetMapping
	public ResponseEntity<List<Cozinha>> listar()
	{
		return ResponseEntity.ok(cadastroCozinha.listar());
	}

	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable long cozinhaId)
	{
		Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);

		if (cozinha != null)
		{
			return ResponseEntity.ok(cozinha);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha)
	{
		return ResponseEntity.ok(cadastroCozinha.salvar(cozinha));
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable long cozinhaId, @RequestBody Cozinha cozinha)
	{
		Cozinha cozinhaAtual = cadastroCozinha.buscar(cozinhaId);

		if (cozinhaAtual != null)
		{
			cozinha.setId(cozinhaId);
			return ResponseEntity.ok(cadastroCozinha.salvar(cozinha));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable long cozinhaId)
	{
		try
		{
			cadastroCozinha.remover(cozinhaId);
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
