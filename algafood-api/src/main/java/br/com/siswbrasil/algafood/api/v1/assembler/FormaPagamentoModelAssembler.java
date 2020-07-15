package br.com.siswbrasil.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.siswbrasil.algafood.api.v1.AlgaLinks;
import br.com.siswbrasil.algafood.api.v1.controller.FormaPagamentoController;
import br.com.siswbrasil.algafood.api.v1.model.FormaPagamentoModel;
import br.com.siswbrasil.algafood.core.security.AlgaSecurity;
import br.com.siswbrasil.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler 
		extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public FormaPagamentoModelAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoModel.class);
	}
	
	@Override
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		FormaPagamentoModel formaPagamentoModel = 
				createModelWithId(formaPagamento.getId(), formaPagamento);
		
		modelMapper.map(formaPagamento, formaPagamentoModel);
		
		if (algaSecurity.podeConsultarFormasPagamento()) {
			formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));
		}
		
		return formaPagamentoModel;
	}
	
	@Override
	public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		CollectionModel<FormaPagamentoModel> collectionModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarFormasPagamento()) {
			collectionModel.add(algaLinks.linkToFormasPagamento());
		}
			
		return collectionModel;
	}
	
}

