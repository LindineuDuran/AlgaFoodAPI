package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService
{
	@Autowired
	private CozinhaRepository cozinhaRepository;

	public List<Cozinha> listar()
	{
		return cozinhaRepository.findAll();
	}

	public Cozinha buscar(long cozinhaId)
	{
		return this.cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de cozinha de código %d", cozinhaId)));
	}

	public Cozinha salvar(Cozinha cozinha)
	{
		return cozinhaRepository.save(cozinha);
	}

	public void remover(long cozinhaId)
	{
		try
		{
			cozinhaRepository.deleteById(cozinhaId);
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cozinha de código %d", cozinhaId));
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso", cozinhaId));
		}
	}
}
