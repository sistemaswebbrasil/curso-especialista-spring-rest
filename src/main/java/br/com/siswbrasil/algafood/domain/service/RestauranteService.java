package br.com.siswbrasil.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.siswbrasil.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.siswbrasil.algafood.domain.model.Cozinha;
import br.com.siswbrasil.algafood.domain.model.Restaurante;
import br.com.siswbrasil.algafood.domain.repository.CozinhaRepository;
import br.com.siswbrasil.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante salvar(Restaurante restaurante) {

		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException("A cozinha " + cozinhaId + " não foi encontrada !");
		}

		return restauranteRepository.salvar(restaurante);
	}

	public Restaurante atualizar(Long id, Restaurante restaurante) {
		Restaurante restauranteSalvo = this.restauranteRepository.buscar(id);

		Cozinha cozinha = null;
		if (restaurante.getCozinha() != null) {
			Long cozinhaId = restaurante.getCozinha().getId();
			cozinha = cozinhaRepository.buscar(cozinhaId);
			if (cozinha == null) {
				throw new EntidadeNaoEncontradaException("A cozinha " + cozinhaId + " não foi encontrada !");
			}
		}

		restauranteSalvo.setCozinha(cozinha);
		restauranteSalvo.setNome(restaurante.getNome());
		restauranteSalvo.setTaxaFrete(restaurante.getTaxaFrete());

		return restauranteRepository.salvar(restauranteSalvo);

	}

}
