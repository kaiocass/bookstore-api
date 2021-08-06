package com.kaio.bookstore.dtos;

import java.io.Serializable;

import com.kaio.bookstore.domain.Livro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class LivroDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String titulo;
	private String nomeAutor;
	private String texto;
	
	public LivroDTO(Livro livro) {
		super();
		this.id = livro.getId();
		this.titulo = livro.getTitulo();
		this.nomeAutor = livro.getNomeAutor();
		this.texto = livro.getTexto();
	}
}
