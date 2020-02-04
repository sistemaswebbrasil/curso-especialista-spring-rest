package br.com.siswbrasil.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
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

		if (estadoRepository.buscar(estadoId) == null) {
			throw new EntidadeRelacionadaNaoEncontradaException(
					String.format("Estado código %s relacionado com a Cidade não existe!", estadoId));
		}

		return cidadeRepository.salvar(obj);
	}

	public Cidade atualizar(Long id, Cidade obj) {

		Long estadoId = obj.getEstado() != null ? obj.getEstado().getId() : null;

		if (( estadoId != null ) && (estadoRepository.buscar(estadoId) == null)) {
			throw new EntidadeRelacionadaNaoEncontradaException(
					String.format("Estado código %s relacionado com a Cidade não existe!", estadoId));
		}

		Cidade cidade = cidadeRepository.buscar(id);

		if (cidade == null) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade código %s não foi encontrado", id));
		}

		cidade.setEstado(obj.getEstado());
		cidade.setNome(obj.getNome());

		return cidadeRepository.salvar(cidade);
	}

	public void excluir(Long id) {

		Cidade cidade = cidadeRepository.buscar(id);

		if (cidade == null) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade código %s não foi encontrado", id));
		}

		cidadeRepository.remover(id);

	}

}
