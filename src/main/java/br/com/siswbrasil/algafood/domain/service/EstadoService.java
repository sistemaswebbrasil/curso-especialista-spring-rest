package br.com.siswbrasil.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.siswbrasil.algafood.domain.exception.EntidadeEmUsoException;
import br.com.siswbrasil.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.siswbrasil.algafood.domain.model.Estado;
import br.com.siswbrasil.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar(Estado obj) {

		return estadoRepository.save(obj);
	}

	public Estado atualizar(Long id, Estado obj) {

		Optional<Estado> estado = estadoRepository.findById(id);

		if (estado.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Estado código %s não foi encontrado", id));
		}

		estado.get().setNome(obj.getNome());

		return estadoRepository.save(estado.get());
	}
	
	public void excluir(Long id) {
		try {
			estadoRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de estado com código %d", id));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removida, pois está em uso", id));
		}
	}	

}
