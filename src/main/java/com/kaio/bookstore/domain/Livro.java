package com.kaio.bookstore.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "livro", schema = "bookstore")
public class Livro implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "O campo Título é obrigatório")
	@Length(min = 3, max = 50, message = "O campo Título deve ter entre 3 e 100 caracteres")
	private String titulo;
	
	@Column(name = "nome_autor")
	@NotEmpty(message = "O campo Nome do Autor é obrigatório")
	@Length(min = 3, max = 50, message = "O campo Nome do Autor  deve ter entre 3 e 100 caracteres")
	private String nomeAutor;
	
	@NotEmpty(message = "O campo Texto é obrigatório")
	@Length(min = 3, max = 1000000, message = "O campo Texto deve ter entre 3 e 1.000.000 caracteres")
	private String texto;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
}
