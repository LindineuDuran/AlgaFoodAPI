package com.lduran.algafood.infrastructure.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lduran.algafood.domain.filter.VendaDiariaFilter;
import com.lduran.algafood.domain.model.Pedido;
import com.lduran.algafood.domain.model.StatusPedido;
import com.lduran.algafood.domain.model.dto.VendaDiaria;
import com.lduran.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService
{
	@Autowired
	private EntityManager manager;

	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset)
	{
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);

		var functionConvertTzDataCriacao = builder.function("convert_tz", Date.class, root.get("dataCriacao"),
				builder.literal("+00:00"), builder.literal(timeOffset));

		var functionDateDataCriacao = builder.function("date", Date.class, root.get("dataCriacao"));

		var selection = builder.construct(VendaDiaria.class, functionDateDataCriacao, builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));

		query.select(selection);
		query.groupBy(functionDateDataCriacao);

		var predicates = new ArrayList<Predicate>();

		if (filtro.getRestauranteId() != null)
		{
			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}

		if (filtro.getDataCriacaoInicio() != null)
		{
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
		}

		if (filtro.getDataCriacaoFim() != null)
		{
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
		}

		predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

		query.where(predicates.toArray(new Predicate[0]));

		return manager.createQuery(query).getResultList();
	}

}
