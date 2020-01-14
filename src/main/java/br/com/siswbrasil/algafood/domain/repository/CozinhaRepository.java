package br.com.siswbrasil.algafood.domain.repository;

import java.util.List;

import br.com.siswbrasil.algafood.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();

	Cozinha buscar(Long id);

	Cozinha salvar(Cozinha cozinha);

	void remover(Cozinha cozinha);

}
