package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.exception.CidadeNaoEncontradaException;
import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.model.Cidade;
import com.lduran.algafood.domain.model.Estado;
import com.lduran.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService
{
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	public List<Cidade> listar()
	{
		return cidadeRepository.findAll();
	}

	public Cidade buscar(long cidadeId)
	{
		return this.cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}

	@Transactional
	public Cidade salvar(Cidade cidade)
	{
		long estadoId = cidade.getEstado().getId();
		Estado estado = cadastroEstado.buscar(estadoId);

		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}

	@Transactional
	public void remover(long cidadeId)
	{
		try
		{
			cidadeRepository.deleteById(cidadeId);
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new CidadeNaoEncontradaException(cidadeId);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}
}
