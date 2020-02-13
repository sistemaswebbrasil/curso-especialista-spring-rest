package br.com.siswbrasil.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.siswbrasil.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.siswbrasil.algafood.domain.exception.EntidadeRelacionadaNaoEncontradaException;
import br.com.siswbrasil.algafood.domain.model.Cidade;
import br.com.siswbrasil.algafood.domain.repository.CidadeRepository;
import br.com.siswbrasil.algafood.domain.repository.EstadoRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade obj) {

		Long estadoId = obj.getEstado().getId();

		if (estadoRepository.findById(estadoId) == null) {
			throw new EntidadeRelacionadaNaoEncontradaException(
					String.format("Estado código %s relacionado com a Cidade não existe!", estadoId));
		}

		return cidadeRepository.save(obj);
	}

	public Cidade atualizar(Long id, Cidade obj) {

		Long estadoId = obj.getEstado() != null ? obj.getEstado().getId() : null;

		if ((estadoId != null) && (estadoRepository.findById(estadoId) == null)) {
			throw new EntidadeRelacionadaNaoEncontradaException(
					String.format("Estado código %s relacionado com a Cidade não existe!", estadoId));
		}

		Optional<Cidade> cidade = cidadeRepository.findById(id);

		if (cidade.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade código %s não foi encontrado", id));
		}

		cidade.get().setEstado(obj.getEstado());
		cidade.get().setNome(obj.getNome());

		return cidadeRepository.save(cidade.get());
	}

	public void excluir(Long id) {

		try {
			cidadeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade código %s não foi encontrado", id));
		}
	}

}
