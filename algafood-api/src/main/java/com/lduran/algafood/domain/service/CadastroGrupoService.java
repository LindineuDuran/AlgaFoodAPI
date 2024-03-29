package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.exception.EntidadeEmUsoException;
import com.lduran.algafood.domain.exception.GrupoNaoEncontradoException;
import com.lduran.algafood.domain.model.Grupo;
import com.lduran.algafood.domain.model.Permissao;
import com.lduran.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService
{
	private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removida, pois está em uso";

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;

	public List<Grupo> listar()
	{
		return grupoRepository.findAll();
	}

	public Grupo buscar(Long grupoId)
	{
		return this.grupoRepository.findById(grupoId).orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}

	@Transactional
	public Grupo salvar(Grupo grupo)
	{
		return grupoRepository.save(grupo);
	}

	@Transactional
	public void remover(Long grupoId)
	{
		try
		{
			grupoRepository.deleteById(grupoId);

			// Libera todas as alterações pendentes no banco de dados.
			grupoRepository.flush();
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new GrupoNaoEncontradoException(grupoId);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}

	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId)
	{
		Grupo grupo = buscar(grupoId);
		Permissao permissao = cadastroPermissaoService.buscar(permissaoId);

		grupo.removerPermissao(permissao);
	}

	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId)
	{
		Grupo grupo = buscar(grupoId);
		Permissao permissao = cadastroPermissaoService.buscar(permissaoId);

		grupo.adicionarPermissao(permissao);
	}
}
