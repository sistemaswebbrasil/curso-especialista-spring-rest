package br.com.siswbrasil.algafood.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.siswbrasil.algafood.domain.exception.EntidadeEmUsoException;
import br.com.siswbrasil.algafood.domain.exception.NegocioException;
import br.com.siswbrasil.algafood.domain.exception.UsuarioNaoEncontradoException;
import br.com.siswbrasil.algafood.domain.model.Grupo;
import br.com.siswbrasil.algafood.domain.model.Usuario;
import br.com.siswbrasil.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	private static final String MSG_USUARIO_EM_USO = "usuário de código %d não pode ser removido, pois está em uso";

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	CadastroGrupoService grupoService;	

	@Transactional
	public Usuario salvar(Usuario usuario) {
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        
        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        
        usuario.setSenha(novaSenha);
    }	

	@Transactional
	public void excluir(Long id) {
		try {
			usuarioRepository.deleteById(id);
			usuarioRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, id));
		}
	}

	public Usuario buscarOuFalhar(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
	    Usuario usuario = buscarOuFalhar(usuarioId);
	    Grupo grupo = grupoService.buscarOuFalhar(grupoId);
	    
	    usuario.removerGrupo(grupo);
	}

	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
	    Usuario usuario = buscarOuFalhar(usuarioId);
	    Grupo grupo = grupoService.buscarOuFalhar(grupoId);
	    
	    usuario.adicionarGrupo(grupo);
	}	

}
