package br.com.siswbrasil.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.siswbrasil.algafood.AlgafoodApiApplication;
import br.com.siswbrasil.algafood.domain.model.Cozinha;

public class InclusaoCozinhaMain {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("brasileira");
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("japonesa");
		
		cozinha1 =  cadastroCozinha.inserir(cozinha1); 
		cozinha2 = cadastroCozinha.inserir(cozinha2);
		
		System.out.printf("%d - %s\n", cozinha1.getId(),cozinha1.getNome());
		System.out.printf("%d - %s\n", cozinha2.getId(),cozinha2.getNome());

	}

}
