package com.lduran.algafood.domain.service;

import java.util.List;

import com.lduran.algafood.domain.filter.VendaDiariaFilter;
import com.lduran.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService
{
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
