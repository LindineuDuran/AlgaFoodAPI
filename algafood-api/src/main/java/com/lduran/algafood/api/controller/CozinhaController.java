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
	public Cozinha buscar(@PathVariable long cozinhaId)
	{
		return cadastroCozinha.buscar(cozinhaId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha)
	{
		return ResponseEntity.ok(cadastroCozinha.salvar(cozinha));
	}

	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable long cozinhaId, @RequestBody Cozinha cozinha)
	{
		Cozinha cozinhaAtual = cadastroCozinha.buscar(cozinhaId);

		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

		return cadastroCozinha.salvar(cozinha);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable long cozinhaId)
	{
		cadastroCozinha.remover(cozinhaId);
	}
}
