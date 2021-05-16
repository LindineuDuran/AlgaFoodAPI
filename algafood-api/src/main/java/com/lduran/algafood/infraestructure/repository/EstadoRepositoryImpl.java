package com.lduran.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lduran.algafood.domain.model.Estado;
import com.lduran.algafood.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository
{
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Estado> listar()
	{
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}

	@Override
	public Estado buscar(long id)
	{
		return manager.find(Estado.class, id);
	}

	@Transactional
	@Override
	public Estado salvar(Estado estado)
	{
		return manager.merge(estado);
	}

	@Transactional
	@Override
	public void remover(long id)
	{
		Estado estado = buscar(id);

		if (estado == null)
		{
			throw new EmptyResultDataAccessException(1);
		}

		manager.remove(estado);
	}
}
