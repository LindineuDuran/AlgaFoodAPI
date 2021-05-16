package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.algafood.domain.model.Estado;
import com.lduran.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService
{
	@Autowired
	private EstadoRepository estadoRepository;

	public List<Estado> listar()
	{
		return estadoRepository.listar();
	}

	public Estado buscar(long estadoId)
	{
		return this.estadoRepository.buscar(estadoId);
	}

	public Estado salvar(Estado estado)
	{
		return estadoRepository.salvar(estado);
	}

	public void remover(long estadoId)
	{
		try
		{
			estadoRepository.remover(estadoId);
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de estado de código %d", estadoId));
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removido, pois está em uso", estadoId));
		}
	}
}
