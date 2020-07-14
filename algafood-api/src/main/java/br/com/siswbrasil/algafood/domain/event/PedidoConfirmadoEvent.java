package br.com.siswbrasil.algafood.domain.event;

import br.com.siswbrasil.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {
	
	private Pedido pedido;

}
