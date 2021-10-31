package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService
{
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso";

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	public List<Restaurante> listar()
	{
		return restauranteRepository.findAll();
	}

	public Restaurante buscar(Long restauranteId)
	{
		return this.restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}

	@Transactional
	public Restaurante salvar(Restaurante restaurante)
	{
		long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cadastroCozinha.buscar(cozinhaId);

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void remover(Long restauranteId)
	{
		try
		{
			restauranteRepository.deleteById(restauranteId);

			// Libera todas as alterações pendentes no banco de dados.
			restauranteRepository.flush();
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new RestauranteNaoEncontradoException(restauranteId);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
	}
}