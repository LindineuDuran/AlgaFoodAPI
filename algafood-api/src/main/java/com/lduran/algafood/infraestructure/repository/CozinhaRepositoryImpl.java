package com.lduran.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.repository.CozinhaRepository;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository
{
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cozinha> todas()
	{
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

	@Override
	public Cozinha porId(long id)
	{
		return manager.find(Cozinha.class, id);
	}

	@Override
	public boolean existe(long id)
	{
		Cozinha cozinha = this.manager.find(Cozinha.class, id);
		if (cozinha != null)
		{
			return true;
		}

		return false;
	}

	@Transactional
	@Override
	public Cozinha adicionar(Cozinha cozinha)
	{
		return manager.merge(cozinha);
	}

	@Transactional
	@Override
	public void remover(Cozinha cozinha)
	{
		cozinha = porId(cozinha.getId());
		manager.remove(cozinha);
	}
}
