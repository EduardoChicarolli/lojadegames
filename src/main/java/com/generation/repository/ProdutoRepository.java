	package com.generation.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	public List<Produto> finAllByTituloContainingIgnoreCase(@Param("titulo")String titulo);

	public List<Produto> findByPrecoLessThanEqualOrderByPreco(BigDecimal preco);
	
	public List<Produto> findByPrecoGreaterThanEqualOrderByPreco(BigDecimal preco);



	
}
