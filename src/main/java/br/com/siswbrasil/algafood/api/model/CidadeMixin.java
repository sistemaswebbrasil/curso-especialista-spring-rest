package br.com.siswbrasil.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.siswbrasil.algafood.domain.model.Estado;

public abstract class CidadeMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
	
}