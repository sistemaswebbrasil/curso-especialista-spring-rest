package br.com.siswbrasil.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.siswbrasil.algafood.domain.exception.EntidadeEmUsoException;
import br.com.siswbrasil.algafood.domain.exception.EstadoNaoEncontradoException;
import br.com.siswbrasil.algafood.domain.model.Estado;
import br.com.siswbrasil.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {
	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			estadoRepository.deleteById(id);
			estadoRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
		}
	}

	public Estado buscarOuFalhar(Long id) {
		return estadoRepository.findById(id)
				.orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}

}
