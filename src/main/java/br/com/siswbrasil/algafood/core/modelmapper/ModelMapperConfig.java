package br.com.siswbrasil.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.siswbrasil.algafood.api.v1.model.EnderecoModel;
import br.com.siswbrasil.algafood.api.v1.model.input.ItemPedidoInput;
import br.com.siswbrasil.algafood.domain.model.Endereco;
import br.com.siswbrasil.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
				Endereco.class, EnderecoModel.class);
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
	    .addMappings(mapper -> mapper.skip(ItemPedido::setId));  
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
		
		return modelMapper;
	}
	
}
