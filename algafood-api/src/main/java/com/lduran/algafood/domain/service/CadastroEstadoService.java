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
	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";
	private static final String MSG_ESTADO_NÃO_ENCONTRADO = "Não existe um cadastro de estado de código %d";

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Estado> listar()
	{
		return estadoRepository.findAll();
	}

	public Estado buscar(long estadoId)
	{
		return this.estadoRepository.findById(estadoId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NÃO_ENCONTRADO, estadoId)));
	}

	public Estado salvar(Estado estado)
	{
		return estadoRepository.save(estado);
	}

	public void remover(long estadoId)
	{
		try
		{
			estadoRepository.deleteById(estadoId);
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NÃO_ENCONTRADO, estadoId));
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}
}
