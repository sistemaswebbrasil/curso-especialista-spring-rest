package br.com.siswbrasil.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.siswbrasil.algafood.api.AlgaLinks;
import br.com.siswbrasil.algafood.api.controller.CidadeController;
import br.com.siswbrasil.algafood.api.model.CidadeModel;
import br.com.siswbrasil.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler 
		extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;	
	
	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}
	
	@Override
	public CidadeModel toModel(Cidade cidade) {
	    CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
	    
	    modelMapper.map(cidade, cidadeModel);
	    
	    cidadeModel.add(algaLinks.linkToCidades("cidades"));
	    
	    cidadeModel.getEstado().add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));
	    
	    return cidadeModel;
	}
	
	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(CidadeController.class).withSelfRel());
	}
	
}
