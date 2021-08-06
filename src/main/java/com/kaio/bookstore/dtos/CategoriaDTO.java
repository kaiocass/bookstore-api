package com.kaio.bookstore.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.kaio.bookstore.domain.Categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "O campo Nome é obrigatório")
	@Length(min = 3, max = 100, message = "O campo Descrição deve ter entre 3 e 100 caracteres")
	private String nome;
	
	@NotEmpty(message = "O campo Descrição é obrigatório")
	@Length(min = 3, max = 200, message = "O campo Descrição deve ter entre 3 e 200 caracteres")
	private String descricao;
	
	public CategoriaDTO(Categoria categoria) {
		super();
		this.id = categoria.getId();
		this.nome = categoria.getNome();
		this.descricao = categoria.getDescricao();
	}
}
