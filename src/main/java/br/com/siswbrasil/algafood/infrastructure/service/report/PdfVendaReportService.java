package br.com.siswbrasil.algafood.infrastructure.service.report;

import org.springframework.stereotype.Service;

import br.com.siswbrasil.algafood.domain.filter.VendaDiariaFilter;
import br.com.siswbrasil.algafood.domain.service.VendaReportService;

@Service
public class PdfVendaReportService implements VendaReportService{

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {

		return null;
	}

}
