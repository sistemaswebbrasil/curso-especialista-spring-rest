package br.com.siswbrasil.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.siswbrasil.algafood.api.assembler.CidadeInputDisassembler;
import br.com.siswbrasil.algafood.api.assembler.CidadeModelAssembler;
import br.com.siswbrasil.algafood.api.controller.openapi.CidadeControllerOpenApi;
import br.com.siswbrasil.algafood.api.model.CidadeModel;
import br.com.siswbrasil.algafood.api.model.input.CidadeInput;
import br.com.siswbrasil.algafood.domain.exception.EstadoNaoEncontradoException;
import br.com.siswbrasil.algafood.domain.exception.NegocioException;
import br.com.siswbrasil.algafood.domain.model.Cidade;
import br.com.siswbrasil.algafood.domain.repository.CidadeRepository;
import br.com.siswbrasil.algafood.domain.service.CidadeService;
import io.swagger.annotations.Api;

@Api(tags = "Cidades")
@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@GetMapping
	public List<CidadeModel> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();

		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}

	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

		return cidadeModelAssembler.toModel(cidade);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			cidade = cidadeService.salvar(cidade);

			return cidadeModelAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			cidadeAtual = cidadeService.salvar(cidadeAtual);

			return cidadeModelAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}

}
