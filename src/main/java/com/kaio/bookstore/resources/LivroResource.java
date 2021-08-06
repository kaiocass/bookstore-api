package com.kaio.bookstore.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kaio.bookstore.domain.Livro;
import com.kaio.bookstore.dtos.LivroDTO;
import com.kaio.bookstore.service.LivroService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(tags = { "Livro" })
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/livros")
@RequiredArgsConstructor
public class LivroResource {

	private final LivroService livroService;
	
	@ApiOperation(value = "Consultar todos os livros de acordo com a categoria")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso", response = LivroDTO.class) })
	@GetMapping
	public ResponseEntity<List<LivroDTO>> findAll(
			@RequestParam(value ="categoria", defaultValue = "0") Integer idCat){
		List<Livro> livros = livroService.findAll(idCat);
		List<LivroDTO> livroDtos = livros.stream()
				.map(livro -> new LivroDTO(livro))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(livroDtos);
	}
	
	@ApiOperation(value = "Consultar livro por id")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso", response = Livro.class) })
	@GetMapping(value = "/{id}")
	public ResponseEntity<Livro> findById(@PathVariable Integer id) {
		Livro livro = livroService.findById(id);
		return ResponseEntity.ok().body(livro);
	}
	
	@ApiOperation(value = "Adicionar novo livro de acordo com a categoria")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Sucesso", response = Livro.class) })
	@PostMapping
	public ResponseEntity<Livro> create(@RequestParam(value = "categoria", defaultValue = "0") Integer idCategoria,
			@Valid @RequestBody Livro livro) {
		Livro novoLivro = livroService.create(idCategoria, livro);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/livros/{id}")
				.buildAndExpand(novoLivro.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Editar livro por id")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso", response = Livro.class) })
	@PutMapping(value = "/{id}")
	public ResponseEntity<Livro> update(@PathVariable Integer id, @Valid @RequestBody Livro livro) {
		Livro novoLivro = livroService.update(id, livro);
		return ResponseEntity.ok().body(novoLivro);
	}
	
	@ApiOperation(value = "Editar livro por id")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso", response = Livro.class) })
	@PatchMapping(value = "/{id}")
	public ResponseEntity<Livro> updatePatch(@PathVariable Integer id, @Valid @RequestBody Livro livro) {
		Livro novoLivro = livroService.update(id, livro);
		return ResponseEntity.ok().body(novoLivro);
	}
	
	@ApiOperation(value = "Deletar livro por id")
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "Sucesso", response = Livro.class) })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Livro> delete(@PathVariable Integer id) {
		livroService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
