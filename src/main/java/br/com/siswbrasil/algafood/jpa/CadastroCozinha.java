package br.com.siswbrasil.algafood.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import br.com.siswbrasil.algafood.domain.model.Cozinha;

@Component
public class CadastroCozinha {

	@PersistenceContext
	private EntityManager manager;

	public List<Cozinha> listar() {

		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();

	}

	@Transactional
	public Cozinha inserir(Cozinha cozinha) {
		return manager.merge(cozinha);

	}

}
