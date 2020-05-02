package br.com.siswbrasil.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.siswbrasil.algafood.api.model.EstadoModel;
import br.com.siswbrasil.algafood.domain.model.FormaPagamanto;

@Component
public class EstadoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public EstadoModel toModel(FormaPagamanto estado) {
		return modelMapper.map(estado, EstadoModel.class);
	}

	public List<EstadoModel> toCollectionModel(List<FormaPagamanto> estados) {
		return estados.stream().map(estado -> toModel(estado)).collect(Collectors.toList());
	}

}
