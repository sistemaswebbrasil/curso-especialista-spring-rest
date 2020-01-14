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
	public Cozinha salvar(Cozinha cozinha) {
		return manager.merge(cozinha);

	}

	public Cozinha buscar(long id) {
		return manager.find(Cozinha.class, id);
	}
	
	@Transactional
	public void remover(Cozinha cozinha) {
		cozinha = buscar(cozinha.getId());
		manager.remove(cozinha);
	}

}
