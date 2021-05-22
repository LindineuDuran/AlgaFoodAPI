package com.lduran.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.model.Cozinha;
import com.lduran.algafood.domain.repository.CozinhaRepository;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository
{
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cozinha> listar()
	{
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

	@Override
	public List<Cozinha> consultarPorNome(String nome)
	{
		return manager.createQuery("from Cozinha where nome like :nome", Cozinha.class)
				.setParameter("nome", "%" + nome + "%").getResultList();
	}

	@Override
	public Cozinha buscar(long id)
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
	public Cozinha salvar(Cozinha cozinha)
	{
		return manager.merge(cozinha);
	}

	@Transactional
	@Override
	public void remover(long id)
	{
		Cozinha cozinha = buscar(id);

		if (cozinha == null)
		{
			throw new EmptyResultDataAccessException(1);
		}

		manager.remove(cozinha);
	}
}
