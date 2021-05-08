package com.lduran.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.lduran.algafood.api.model.CozinhasXmlWrapper;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController
{
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@GetMapping
	public ResponseEntity<List<Cozinha>> listar()
	{
		return ResponseEntity.ok(cozinhaRepository.todas());
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<CozinhasXmlWrapper> listarXml()
	{
		return ResponseEntity.ok(new CozinhasXmlWrapper(cozinhaRepository.todas()));
	}

	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId)
	{
		if (!this.cozinhaRepository.existe(cozinhaId))
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.cozinhaRepository.porId(cozinhaId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha)
	{
		return ResponseEntity.ok(cozinhaRepository.adicionar(cozinha));
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha)
	{
		if (!this.cozinhaRepository.existe(cozinhaId))
		{
			return ResponseEntity.notFound().build();
		}

		cozinha.setId(cozinhaId);
		return ResponseEntity.ok(cozinhaRepository.adicionar(cozinha));
	}

	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId)
	{
		try
		{
			if (!this.cozinhaRepository.existe(cozinhaId))
			{
				return ResponseEntity.notFound().build();
			}

			Cozinha cozinha = cozinhaRepository.porId(cozinhaId);
			cozinhaRepository.remover(cozinha);

			return ResponseEntity.noContent().build();
		}
		catch (DataIntegrityViolationException e)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
