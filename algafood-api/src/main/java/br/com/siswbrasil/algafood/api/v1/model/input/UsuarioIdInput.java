package br.com.siswbrasil.algafood.api.v1.model.input;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioIdInput {

	@NotNull
	private Long id;

}
