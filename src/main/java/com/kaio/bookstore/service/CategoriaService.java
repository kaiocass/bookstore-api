package com.kaio.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kaio.bookstore.domain.Categoria;
import com.kaio.bookstore.dtos.CategoriaDTO;
import com.kaio.bookstore.repositories.CategoriaRepository;
import com.kaio.bookstore.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> findAll() {
		return this.categoriaRepository.findAll();
	}
	
	public Categoria findById(Integer id) {
		Optional<Categoria> categoria = this.categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + " e Tipo: " +  Categoria.class.getName()));
	}

	public Categoria create(Categoria categoria) {
		categoria.setId(null);
		return this.categoriaRepository.save(categoria);
	}
	
	public Categoria update(Integer id, CategoriaDTO categoriaDto) {
		Categoria categoria = findById(id);
		
		categoria = Categoria.builder()
				.id(categoria.getId())
				.nome(categoriaDto.getNome())
				.descricao(categoriaDto.getDescricao()).build();
		
		
		return this.categoriaRepository.save(categoria);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			this.categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new com.kaio.bookstore.service.exceptions.DataIntegrityViolationException(
					"Categoria nao pode ser deletada! Possui livros associados");
		}
	}
}
