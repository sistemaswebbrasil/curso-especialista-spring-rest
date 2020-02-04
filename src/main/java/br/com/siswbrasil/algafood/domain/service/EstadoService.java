package br.com.siswbrasil.algafood.domain.service;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.siswbrasil.algafood.domain.exception.EntidadeEmUsoException;
import br.com.siswbrasil.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.siswbrasil.algafood.domain.exception.EntidadeRelacionadaNaoEncontradaException;
import br.com.siswbrasil.algafood.domain.model.Estado;
import br.com.siswbrasil.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar(Estado obj) {

		return estadoRepository.salvar(obj);
	}

	public Estado atualizar(Long id, Estado obj) {

		Estado estado = estadoRepository.buscar(id);

		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Estado código %s não foi encontrado", id));
		}

		estado.setNome(obj.getNome());

		return estadoRepository.salvar(estado);
	}

	public void excluir(Long id) {

		Estado estado = estadoRepository.buscar(id);

		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Estado código %s não foi encontrado", id));
		}

		try {
			estadoRepository.remover(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Não é possivel excluir etado , pois está em uso");
		}

	}

}
