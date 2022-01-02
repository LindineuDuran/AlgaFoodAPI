package com.lduran.algafood.domain.service;

import com.lduran.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService
{
	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
