package br.com.siswbrasil.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.siswbrasil.algafood.domain.model.FormaPagamanto;

@Repository
public interface EstadoRepository extends JpaRepository<FormaPagamanto, Long> {

}
