package com.lduran.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lduran.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.lduran.algafood.domain.model.Permissao;
import com.lduran.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService
{
	@Autowired
	private PermissaoRepository permissaoRepository;

	public Permissao buscar(Long permissaoId)
	{
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
	}
}
