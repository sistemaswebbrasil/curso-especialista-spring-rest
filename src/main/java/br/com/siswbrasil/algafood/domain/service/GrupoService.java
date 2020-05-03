package br.com.siswbrasil.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.siswbrasil.algafood.domain.exception.EntidadeEmUsoException;
import br.com.siswbrasil.algafood.domain.exception.GrupoNaoEncontradoException;
import br.com.siswbrasil.algafood.domain.model.Grupo;
import br.com.siswbrasil.algafood.domain.model.Permissao;
import br.com.siswbrasil.algafood.domain.repository.GrupoRepository;

@Service
public class GrupoService {
	private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso";

	@Autowired
	GrupoRepository grupoRepository;
	
	@Autowired
	PermissaoService permissaoService;	

	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			grupoRepository.deleteById(id);
			grupoRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
		}
	}

	public Grupo buscarOuFalhar(Long id) {
		return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}
	
	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
	    Grupo grupo = buscarOuFalhar(grupoId);
	    Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
	    
	    grupo.removerPermissao(permissao);
	}

	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
	    Grupo grupo = buscarOuFalhar(grupoId);
	    Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
	    
	    grupo.adicionarPermissao(permissao);
	}	

}
