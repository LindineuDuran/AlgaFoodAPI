package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.repository.CozinhaRepository;
import com.lduran.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService
{
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public List<Restaurante> listar()
	{
		return restauranteRepository.findAll();
	}

	public Restaurante buscar(Long restauranteId)
	{
		return this.restauranteRepository.findById(restauranteId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de restaurante de código %d", restauranteId)));
	}

	public Restaurante salvar(Restaurante restaurante)
	{
		long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = this.cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format("Não existe um cadastro de cozinha de código %d", cozinhaId)));

		if (cozinha == null)
		{
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cozinha de código %d", cozinhaId));
		}

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	public void remover(Long restauranteId)
	{
		try
		{
			restauranteRepository.deleteById(restauranteId);
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de restaurante de código %d", restauranteId));
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(
					String.format("Restaurante de código %d não pode ser removido, pois está em uso", restauranteId));
		}
	}
}