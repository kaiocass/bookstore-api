package com.kaio.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaio.bookstore.domain.Categoria;
import com.kaio.bookstore.domain.Livro;
import com.kaio.bookstore.repositories.LivroRepository;
import com.kaio.bookstore.service.exceptions.ObjectNotFoundException;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	public List<Livro> findAll(Integer idCat) {
		categoriaService.findById(idCat);
		return livroRepository.findAllByCategoria(idCat);
	}
	
	public Livro findById(Integer id) {
		Optional<Livro> livro = this.livroRepository.findById(id);
		return livro.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + " e Tipo: " +  Livro.class.getName()));
	}
	
	public Livro update(Integer id, Livro livro) {
		Livro novoLivro = findById(id);
		updateData(novoLivro, livro);
		return livroRepository.save(novoLivro);
	}

	private void updateData(Livro novoLivro, Livro livro) {
		novoLivro.setTitulo(livro.getTitulo());
		novoLivro.setNomeAutor(livro.getNomeAutor());
		novoLivro.setTexto(livro.getTexto());
	}

	public Livro create(Integer idCategoria, Livro livro) {
		livro.setId(null);
		Categoria categoria = categoriaService.findById(idCategoria);
		livro.setCategoria(categoria);
		return livroRepository.save(livro);
	}

	public void delete(Integer id) {
		Livro livro = findById(id);
		livroRepository.delete(livro);
	}
}
