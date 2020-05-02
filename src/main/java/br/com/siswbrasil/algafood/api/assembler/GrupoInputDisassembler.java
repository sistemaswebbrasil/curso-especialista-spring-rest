package br.com.siswbrasil.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.siswbrasil.algafood.api.model.input.GrupoInput;
import br.com.siswbrasil.algafood.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Grupo toDomainObject(GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}

	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}

}
