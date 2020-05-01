package br.com.siswbrasil.algafood;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.siswbrasil.algafood.domain.repository.RestauranteRepository;

@SpringBootApplication
public class AlgafoodApiApplication {

	@Autowired
	RestauranteRepository repository;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}

}
