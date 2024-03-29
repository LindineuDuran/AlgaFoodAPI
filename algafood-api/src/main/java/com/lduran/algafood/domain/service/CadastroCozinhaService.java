package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService
{
	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public List<Cozinha> listar()
	{
		return cozinhaRepository.findAll();
	}

	public Page<Cozinha> listarPage(Pageable pageable)
	{
		return cozinhaRepository.findAll(pageable);
	}

	public Cozinha buscar(long cozinhaId)
	{
		return this.cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}

	@Transactional
	public Cozinha salvar(Cozinha cozinha)
	{
		return cozinhaRepository.save(cozinha);
	}

	@Transactional
	public void remover(long cozinhaId)
	{
		try
		{
			cozinhaRepository.deleteById(cozinhaId);

			// Libera todas as alterações pendentes no banco de dados.
			cozinhaRepository.flush();
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new CozinhaNaoEncontradaException(cozinhaId);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}
}
