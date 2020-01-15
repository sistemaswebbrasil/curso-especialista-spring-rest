package br.com.siswbrasil.algafood.domain.repository;

import java.util.List;

import br.com.siswbrasil.algafood.domain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao> listar();

	Permissao buscar(Long id);

	Permissao salvar(Permissao permissao);

	void remover(Permissao permissao);

}
