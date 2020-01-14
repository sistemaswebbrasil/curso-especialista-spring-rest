package br.com.siswbrasil.algafood;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.siswbrasil.algafood.domain.model.Restaurante;
import br.com.siswbrasil.algafood.domain.repository.RestauranteRepository;

@SpringBootApplication
public class AlgafoodApiApplication {

	@Autowired
	RestauranteRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}

	@PostConstruct
	public void testerestaurante() {
		Restaurante novo = new Restaurante();
		novo.setNome("Sabor Mineiro");

		novo = repository.salvar(novo);

		System.out.println("Restaurante adicionado : " + novo);

		Restaurante atualizar = new Restaurante();
		atualizar.setId(1L);
		atualizar.setNome("Bob's");
		atualizar.setTaxaFrete(new BigDecimal(5.5));
		atualizar = repository.salvar(atualizar);

		System.out.println("Restaurante alterado: " + atualizar);

		Restaurante remover = new Restaurante();
		remover.setId(2L);
		repository.remover(remover);

		List<Restaurante> listar = repository.listar();

		for (Restaurante restaurante : listar) {
			System.out.printf("%s - %f - %s\n", restaurante.getNome(), restaurante.getTaxaFrete(),
					restaurante.getCozinha() != null ? restaurante.getCozinha().getNome() : "");

		}
	}

}
