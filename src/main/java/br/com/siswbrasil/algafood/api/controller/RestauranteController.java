package br.com.siswbrasil.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.siswbrasil.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.siswbrasil.algafood.domain.model.Restaurante;
import br.com.siswbrasil.algafood.domain.repository.RestauranteRepository;
import br.com.siswbrasil.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
		Restaurante restaurante = restauranteRepository.buscar(id);
		if (restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> incluir(@RequestBody Restaurante restaurante) {
		try {
			restaurante = restauranteService.salvar(restaurante);
			return ResponseEntity.ok(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (javax.persistence.EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<Restaurante> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
		Restaurante restauranteAtual = restauranteRepository.buscar(id);

		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(restaurante, restauranteAtual, "id");

		restauranteAtual = restauranteRepository.salvar(restauranteAtual);

		return ResponseEntity.ok(restauranteAtual);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long id) {
		try {
			Restaurante restaurante = restauranteRepository.buscar(id);

			if (restaurante != null) {
				restauranteRepository.remover(restaurante);

				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.notFound().build();
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
