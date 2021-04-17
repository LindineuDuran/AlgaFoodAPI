package com.lduran.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.algafood.domain.model.Estado;
import com.lduran.algafood.domain.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController
{
	@Autowired
	private EstadoRepository estadoRepository;

	@GetMapping
	public List<Estado> listar()
	{
		return estadoRepository.todos();
	}

	@GetMapping("/{cozinhaId}")
	public Estado buscar(@PathVariable Long cozinhaId)
	{
		return estadoRepository.porId(cozinhaId);
	}
}
