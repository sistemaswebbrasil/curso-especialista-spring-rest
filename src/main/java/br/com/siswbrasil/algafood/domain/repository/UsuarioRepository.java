package br.com.siswbrasil.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.siswbrasil.algafood.domain.model.Usuario;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

}
