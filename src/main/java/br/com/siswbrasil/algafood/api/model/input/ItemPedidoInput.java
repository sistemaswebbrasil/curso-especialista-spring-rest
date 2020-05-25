package br.com.siswbrasil.algafood.api.model.input;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPedidoInput {

    @NotNull
    private Long produtoId;
    
    @NotNull
    @Min(1)
    private Integer quantidade;
    
    private String observacao;   
}
