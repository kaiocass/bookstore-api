package com.kaio.bookstore.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kaio.bookstore.domain.Categoria;
import com.kaio.bookstore.dtos.CategoriaDTO;
import com.kaio.bookstore.service.CategoriaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(tags = { "Categoria" })
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/categorias")
@RequiredArgsConstructor
public class CategoriaResource {

	private final CategoriaService categoriaService;
	
	@ApiOperation(value = "Consultar todas as categorias")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso", response = CategoriaDTO.class) })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> categorias = categoriaService.findAll();
		List<CategoriaDTO> categoriaDTOs = categorias.stream()
				.map(categoria -> new CategoriaDTO(categoria))
				.collect(Collectors.toList());
		return ResponseEntity.ok(categoriaDTOs);
	}
	
	@ApiOperation(value = "Consultar todas as categorias por id")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso", response = Categoria.class) })
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Integer id) {
		Categoria categoria = categoriaService.findById(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@ApiOperation(value = "Adicionar nova categoria")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Sucesso", response = Categoria.class) })
	@PostMapping
	public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria categoria) {
		categoria = categoriaService.create(categoria);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(categoria.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Editar categoria de acordo com  o id")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso", response = CategoriaDTO.class) })
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoriaDTO> update(@PathVariable Integer id,
			@Valid @RequestBody CategoriaDTO categoriaDto) {
		
		Categoria novaCategoria = categoriaService.update(id, categoriaDto);
		return ResponseEntity.ok().body(new CategoriaDTO(novaCategoria));
	}
	
	@ApiOperation(value = "Deletar categoria por id")
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "Sucesso", response = Void.class) })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		categoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
