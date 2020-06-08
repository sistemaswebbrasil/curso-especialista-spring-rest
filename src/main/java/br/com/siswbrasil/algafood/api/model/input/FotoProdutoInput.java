package br.com.siswbrasil.algafood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import br.com.siswbrasil.algafood.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

	@FileSize(max = "500KB")
	@NotNull
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;

}