package com.lduran.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.model.Restaurante;
import com.lduran.algafood.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository
{
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> listar()
	{
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Override
	public Restaurante buscar(long id)
	{
		return manager.find(Restaurante.class, id);
	}

	@Transactional
	@Override
	public Restaurante salvar(Restaurante restaurante)
	{
		return manager.merge(restaurante);
	}

	@Transactional
	@Override
	public void remover(long id)
	{
		Restaurante restaurante = buscar(id);

		if (restaurante == null)
		{
			throw new EmptyResultDataAccessException(1);
		}

		manager.remove(id);
	}
}
