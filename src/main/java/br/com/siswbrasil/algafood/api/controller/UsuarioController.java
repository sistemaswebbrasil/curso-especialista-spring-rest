package br.com.siswbrasil.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.siswbrasil.algafood.api.assembler.UsuarioInputDisassembler;
import br.com.siswbrasil.algafood.api.assembler.UsuarioModelAssembler;
import br.com.siswbrasil.algafood.api.model.UsuarioModel;
import br.com.siswbrasil.algafood.api.model.input.SenhaInput;
import br.com.siswbrasil.algafood.api.model.input.UsuarioComSenhaInput;
import br.com.siswbrasil.algafood.api.model.input.UsuarioInput;
import br.com.siswbrasil.algafood.domain.model.Usuario;
import br.com.siswbrasil.algafood.domain.repository.UsuarioRepository;
import br.com.siswbrasil.algafood.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;

	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;

	@GetMapping
	public List<UsuarioModel> listar() {
		List<Usuario> todosUsuarios = usuarioRepository.findAll();

		return usuarioModelAssembler.toCollectionModel(todosUsuarios);
	}

	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

		return usuarioModelAssembler.toModel(usuario);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		usuario = usuarioService.salvar(usuario);

		return usuarioModelAssembler.toModel(usuario);
	}

	@PutMapping("/{usuarioId}")
	public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
		usuarioAtual = usuarioService.salvar(usuarioAtual);

		return usuarioModelAssembler.toModel(usuarioAtual);
	}

	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
		usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
	}

}