package com.lduran.algafood.domain.repository;

import com.lduran.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries
{

	FotoProduto save(FotoProduto foto);

	void delete(FotoProduto foto);
}