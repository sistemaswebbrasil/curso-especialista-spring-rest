package br.com.siswbrasil.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.siswbrasil.algafood.AlgafoodApiApplication;
import br.com.siswbrasil.algafood.domain.model.Cozinha;

public class BuscaCozinhaMain {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CadastroCozinha cadastroCozinha =  applicationContext.getBean(CadastroCozinha.class);
		
		Cozinha cozinha = cadastroCozinha.buscar(1L);
		
		System.out.println(cozinha.getNome());

	}

}
