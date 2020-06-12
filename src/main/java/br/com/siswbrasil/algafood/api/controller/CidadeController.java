package br.com.siswbrasil.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.siswbrasil.algafood.api.assembler.CidadeInputDisassembler;
import br.com.siswbrasil.algafood.api.assembler.CidadeModelAssembler;
import br.com.siswbrasil.algafood.api.model.CidadeModel;
import br.com.siswbrasil.algafood.api.model.input.CidadeInput;
import br.com.siswbrasil.algafood.domain.exception.EstadoNaoEncontradoException;
import br.com.siswbrasil.algafood.domain.exception.NegocioException;
import br.com.siswbrasil.algafood.domain.model.Cidade;
import br.com.siswbrasil.algafood.domain.repository.CidadeRepository;
import br.com.siswbrasil.algafood.domain.service.CidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeModel> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}
	
	@ApiOperation("Busca uma cidade por ID")
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(
			@ApiParam(value = "ID de uma cidade", example = "1")
			@PathVariable Long cidadeId) {
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		
		return cidadeModelAssembler.toModel(cidade);
	}
	
	@ApiOperation("Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
			@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			cidade = cidadeService.salvar(cidade);
			
			return cidadeModelAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@ApiOperation("Atualiza uma cidade por ID")
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable Long cidadeId,
			
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
			@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			
			cidadeAtual = cidadeService.salvar(cidadeAtual);
			
			return cidadeModelAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@ApiOperation("Exclui uma cidade por ID")
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(
			@ApiParam(value = "ID de uma cidade", example = "1")
			@PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);	
	}
	
}
