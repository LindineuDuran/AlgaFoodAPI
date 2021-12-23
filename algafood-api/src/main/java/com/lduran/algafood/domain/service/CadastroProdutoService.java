package com.lduran.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.lduran.algafood.domain.model.Produto;
import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService
{
	@Autowired
	private ProdutoRepository produtoRepository;

	public List<Produto> listar(Restaurante restaurante)
	{
		return produtoRepository.findByRestaurante(restaurante);
	}

	public List<Produto> listarAtivos(Restaurante restaurante)
	{
		return produtoRepository.findAtivosByRestaurante(restaurante);
	}

	public Produto buscar(Long restauranteId, Long produtoId)
	{
		return this.produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
	}

	@Transactional
	public Produto salvar(Produto produto)
	{
		return produtoRepository.save(produto);
	}
}
