package br.com.siswbrasil.algafood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.siswbrasil.algafood.domain.repository.RestauranteRepository;

@SpringBootApplication
public class AlgafoodApiApplication {

	@Autowired
	RestauranteRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}

}
