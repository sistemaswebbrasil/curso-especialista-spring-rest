package br.com.siswbrasil.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.siswbrasil.algafood.api.ResourceUriHelper;
import br.com.siswbrasil.algafood.api.v1.assembler.CidadeInputDisassembler;
import br.com.siswbrasil.algafood.api.v1.assembler.CidadeModelAssembler;
import br.com.siswbrasil.algafood.api.v1.model.CidadeModel;
import br.com.siswbrasil.algafood.api.v1.model.input.CidadeInput;
import br.com.siswbrasil.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import br.com.siswbrasil.algafood.core.security.CheckSecurity;
import br.com.siswbrasil.algafood.domain.exception.EstadoNaoEncontradoException;
import br.com.siswbrasil.algafood.domain.exception.NegocioException;
import br.com.siswbrasil.algafood.domain.model.Cidade;
import br.com.siswbrasil.algafood.domain.repository.CidadeRepository;
import br.com.siswbrasil.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@CheckSecurity.Cidades.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<CidadeModel> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}
	
	@CheckSecurity.Cidades.PodeConsultar
	@Override
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		return cidadeModelAssembler.toModel(cidade);
	}
	
	@CheckSecurity.Cidades.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			cidade = cadastroCidade.salvar(cidade);
			
			CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
			
			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());
			
			return cidadeModel;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Cidades.PodeEditar
	@Override
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable Long cidadeId,
			@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			
			cidadeAtual = cadastroCidade.salvar(cidadeAtual);
			
			return cidadeModelAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Cidades.PodeEditar
	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);	
	}
	
}
