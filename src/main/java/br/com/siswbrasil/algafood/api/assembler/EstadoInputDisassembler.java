package br.com.siswbrasil.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.siswbrasil.algafood.api.model.input.EstadoInput;
import br.com.siswbrasil.algafood.domain.model.FormaPagamanto;

@Component
public class EstadoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamanto toDomainObject(EstadoInput estadoInput) {
		return modelMapper.map(estadoInput, FormaPagamanto.class);
	}
	
	public void copyToDomainObject(EstadoInput estadoInput, FormaPagamanto estado) {
		modelMapper.map(estadoInput, estado);
	}
	
}
