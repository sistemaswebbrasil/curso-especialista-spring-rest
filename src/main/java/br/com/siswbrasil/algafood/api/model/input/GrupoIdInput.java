package br.com.siswbrasil.algafood.api.model.input;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoIdInput {
	
	@NotNull
	private Long id;

}
