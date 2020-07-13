package br.com.siswbrasil.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.siswbrasil.algafood.api.assembler.UsuarioModelAssembler;
import br.com.siswbrasil.algafood.api.model.UsuarioModel;
import br.com.siswbrasil.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import br.com.siswbrasil.algafood.domain.model.Restaurante;
import br.com.siswbrasil.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis",
produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;
    
    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;
    
    @GetMapping 
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        
        return usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis());
    }
    
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
    	restauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }
    
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
    	restauranteService.associarResponsavel(restauranteId, usuarioId);
    }
}
