package br.com.siswbrasil.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import br.com.siswbrasil.algafood.api.assembler.CozinhaInputDisassembler;
import br.com.siswbrasil.algafood.api.assembler.CozinhaModelAssembler;
import br.com.siswbrasil.algafood.api.model.CozinhaModel;
import br.com.siswbrasil.algafood.api.model.input.CozinhaInput;
import br.com.siswbrasil.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import br.com.siswbrasil.algafood.domain.model.Cozinha;
import br.com.siswbrasil.algafood.domain.repository.CozinhaRepository;
import br.com.siswbrasil.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;	
	
	@Override
	@GetMapping
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssembler);

		  
		return cozinhasPagedModel;
	}
	
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		
		return cozinhaModelAssembler.toModel(cozinha);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)  
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		cozinha = cozinhaService.salvar(cozinha);
		
		return cozinhaModelAssembler.toModel(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId,
			@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
		
		return cozinhaModelAssembler.toModel(cozinhaAtual);
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cozinhaService.excluir(cozinhaId);
	}
	
}