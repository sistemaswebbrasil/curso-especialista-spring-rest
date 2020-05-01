package br.com.siswbrasil.algafood.api.model.mixin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.siswbrasil.algafood.domain.model.Restaurante;

public abstract class CozinhaMixin {

	@JsonIgnore
	private List<Restaurante> restaurantes;
	
}