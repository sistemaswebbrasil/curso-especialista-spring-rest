package br.com.siswbrasil.algafood.domain.service;

import java.util.List;

import br.com.siswbrasil.algafood.domain.filter.VendaDiariaFilter;
import br.com.siswbrasil.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);
	
}