package com.kaio.bookstore.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "categoria", schema = "bookstore")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "O campo Nome é obrigatório")
	@Length(min = 3, max = 100, message = "O campo Descrição deve ter entre 3 e 100 caracteres")
	private String nome;
	
	@NotEmpty(message = "O campo Descrição é obrigatório")
	@Length(min = 3, max = 200, message = "O campo Descrição deve ter entre 3 e 200 caracteres")
	private String descricao;
	
	@JsonIgnore
	@Builder.Default
	@OneToMany(mappedBy = "categoria")
	private List<Livro> livros = new ArrayList<>();
}
