package br.com.siswbrasil.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.siswbrasil.algafood.api.controller.CozinhaController;
import br.com.siswbrasil.algafood.api.model.CozinhaModel;
import br.com.siswbrasil.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler 
		extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	}
	
	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
		CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);
		
		cozinhaModel.add(linkTo(CozinhaController.class).withRel("cozinhas"));
		
		return cozinhaModel;
	}
	
}
