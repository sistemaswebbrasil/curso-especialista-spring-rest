package br.com.siswbrasil.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.siswbrasil.algafood.api.AlgaLinks;
import br.com.siswbrasil.algafood.api.controller.CidadeController;
import br.com.siswbrasil.algafood.api.controller.FormaPagamentoController;
import br.com.siswbrasil.algafood.api.controller.PedidoController;
import br.com.siswbrasil.algafood.api.controller.RestauranteController;
import br.com.siswbrasil.algafood.api.controller.RestauranteProdutoController;
import br.com.siswbrasil.algafood.api.controller.UsuarioController;
import br.com.siswbrasil.algafood.api.model.PedidoModel;
import br.com.siswbrasil.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		pedidoModel.add(algaLinks.linkToPedidos());

		pedidoModel.getRestaurante().add(
				linkTo(methodOn(RestauranteController.class).buscar(pedido.getRestaurante().getId())).withSelfRel());

		pedidoModel.getCliente()
				.add(linkTo(methodOn(UsuarioController.class).buscar(pedido.getCliente().getId())).withSelfRel());

		pedidoModel.getFormaPagamento()
				.add(linkTo(methodOn(FormaPagamentoController.class).buscar(pedido.getFormaPagamento().getId(), null))
						.withSelfRel());

		pedidoModel.getEnderecoEntrega().getCidade()
				.add(linkTo(methodOn(CidadeController.class).buscar(pedido.getEnderecoEntrega().getCidade().getId()))
						.withSelfRel());

		pedidoModel.getItens().forEach(item -> {
			item.add(linkTo(methodOn(RestauranteProdutoController.class).buscar(pedidoModel.getRestaurante().getId(),
					item.getProdutoId())).withRel("produto"));
		});

		return pedidoModel;
	}

}
