package br.com.siswbrasil.algafood.domain.service;

import br.com.siswbrasil.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
	
}
