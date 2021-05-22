package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.algafood.domain.model.Cidade;
import com.lduran.algafood.domain.model.Estado;
import com.lduran.algafood.domain.repository.CidadeRepository;
import com.lduran.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService
{
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Cidade> listar()
	{
		return cidadeRepository.findAll();
	}

	public Cidade buscar(long cidadeId)
	{
		return this.cidadeRepository.findById(cidadeId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de cidade de código %d", cidadeId)));
	}

	public Cidade salvar(Cidade cidade)
	{
		long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.findById(estadoId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de estado de código %d", estadoId)));

		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}

	public void remover(long cidadeId)
	{
		try
		{
			cidadeRepository.deleteById(cidadeId);
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cidade de código %d", cidadeId));
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
		}
	}
}
