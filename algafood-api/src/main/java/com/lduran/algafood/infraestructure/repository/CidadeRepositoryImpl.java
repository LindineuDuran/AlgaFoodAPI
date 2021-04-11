package com.lduran.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.model.Cidade;
import com.lduran.algafood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository
{
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cidade> todas()
	{
		return manager.createQuery("from Cozinha", Cidade.class).getResultList();
	}

	@Override
	public Cidade porId(long id)
	{
		return manager.find(Cidade.class, id);
	}

	@Transactional
	@Override
	public Cidade adicionar(Cidade cidade)
	{
		return manager.merge(cidade);
	}

	@Transactional
	@Override
	public void remover(Cidade cidade)
	{
		cidade = porId(cidade.getId());
		manager.remove(cidade);
	}
}
