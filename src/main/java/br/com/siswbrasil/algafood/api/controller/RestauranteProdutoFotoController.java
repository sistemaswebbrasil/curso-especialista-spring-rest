package br.com.siswbrasil.algafood.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.siswbrasil.algafood.api.assembler.FotoProdutoModelAssembler;
import br.com.siswbrasil.algafood.api.model.FotoProdutoModel;
import br.com.siswbrasil.algafood.api.model.input.FotoProdutoInput;
import br.com.siswbrasil.algafood.domain.model.FotoProduto;
import br.com.siswbrasil.algafood.domain.model.Produto;
import br.com.siswbrasil.algafood.domain.service.CatalogoFotoProdutoService;
import br.com.siswbrasil.algafood.domain.service.ProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private ProdutoService cadastroProduto;
	
	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto,arquivo.getInputStream());
		
		return fotoProdutoModelAssembler.toModel(fotoSalva);
	}
	
	@GetMapping
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId) {
	    FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
	    
	    return fotoProdutoModelAssembler.toModel(fotoProduto);
	}	
	

	@GetMapping("/image")
	public ResponseEntity<byte[]> recuperar(
			@PathVariable Long restauranteId,
			@PathVariable Long produtoId
	) throws IOException {
		cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		
		FotoProduto  fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);		
		byte[] bytesPdf = catalogoFotoProduto.recuperarFoto(fotoProduto.getNomeArquivo()).readAllBytes();
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;");		
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.headers(headers)
				.body(bytesPdf);		
	}
	
}
