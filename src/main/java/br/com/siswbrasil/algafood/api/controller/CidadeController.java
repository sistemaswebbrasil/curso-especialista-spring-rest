package br.com.siswbrasil.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

import br.com.siswbrasil.algafood.domain.model.Cidade;
import br.com.siswbrasil.algafood.domain.repository.CidadeRepository;
import br.com.siswbrasil.algafood.domain.service.CidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@GetMapping("/{id}")
	public Cidade buscar(@PathVariable Long id) {
		return cidadeService.buscarOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		return cidadeService.salvar(cidade);
	}

	@PutMapping("/{id}")
	public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
		Cidade cidadeAtual = cidadeService.buscarOuFalhar(id);

		BeanUtils.copyProperties(cidade, cidadeAtual, "id");

		return cidadeService.salvar(cidadeAtual);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cidadeService.excluir(id);
	}

}
