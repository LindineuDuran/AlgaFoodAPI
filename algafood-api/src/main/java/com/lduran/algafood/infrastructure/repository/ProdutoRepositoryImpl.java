package com.lduran.algafood.infrastructure.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.model.FotoProduto;
import com.lduran.algafood.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries
{
	@Autowired
	private EntityManager manager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto foto)
	{
		return manager.merge(foto);
	}

	@Override
	public void delete(FotoProduto foto)
	{
		manager.remove(foto);
	}
}
