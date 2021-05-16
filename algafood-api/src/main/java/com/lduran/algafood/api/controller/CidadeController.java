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
import com.lduran.algafood.domain.model.Cidade;
import com.lduran.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController
{
	@Autowired
	private CadastroCidadeService cadastroCidade;

	@GetMapping
	public ResponseEntity<List<Cidade>> listar()
	{
		return ResponseEntity.ok(cadastroCidade.listar());
	}

	@GetMapping("/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable long cidadeId)
	{
		Cidade cidade = cadastroCidade.buscar(cidadeId);

		if (cidade != null)
		{
			return ResponseEntity.ok(cidade);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade)
	{
		try
		{
			cidade = cadastroCidade.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		}
		catch (EntidadeNaoEncontradaException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> atualizar(@PathVariable long cidadeId, @RequestBody Cidade cidade)
	{
		try
		{
			Cidade cidadeAtual = cadastroCidade.buscar(cidadeId);

			if (cidadeAtual != null)
			{
				cidade.setId(cidadeId);
				return ResponseEntity.ok(cadastroCidade.salvar(cidade));
			}

			return ResponseEntity.notFound().build();
		}
		catch (EntidadeNaoEncontradaException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<Cidade> remover(@PathVariable long cidadeId)
	{
		try
		{
			cadastroCidade.remover(cidadeId);
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
