package com.kaio.bookstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kaio.bookstore.domain.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer>{

	@Query("SELECT l FROM Livro l WHERE l.categoria.id = :idCat ORDER BY l.titulo")
	List<Livro> findAllByCategoria(@Param("idCat") Integer idCat);

}
