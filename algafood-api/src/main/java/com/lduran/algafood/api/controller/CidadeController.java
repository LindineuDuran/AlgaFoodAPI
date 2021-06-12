package com.lduran.algafood.api.controller;

import java.util.List;

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
	public Cidade buscar(@PathVariable long cidadeId)
	{
		return cadastroCidade.buscar(cidadeId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade)
	{
		return cidade = cadastroCidade.salvar(cidade);
	}

	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@PathVariable long cidadeId, @RequestBody Cidade cidade)
	{
		Cidade cidadeAtual = cadastroCidade.buscar(cidadeId);

		BeanUtils.copyProperties(cidade, cidadeAtual, "id");

		return cadastroCidade.salvar(cidadeAtual);
	}

	@DeleteMapping("/{cidadeId}")
	public void remover(@PathVariable long cidadeId)
	{
		cadastroCidade.remover(cidadeId);
	}
}
