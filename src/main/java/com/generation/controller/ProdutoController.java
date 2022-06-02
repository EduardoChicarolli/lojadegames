package com.generation.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.generation.model.Produto;
import com.generation.repository.CategoriaRepository;
import com.generation.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
		@GetMapping
		public ResponseEntity<List<Produto>>getAll(@PathVariable String titulo){  //está certo?
			return ResponseEntity.ok(produtoRepository.findAll());
	}	
		@GetMapping("/{id}")
		public ResponseEntity<Produto> getById(@PathVariable Long id){
			return produtoRepository.findById(id)
					.map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity.notFound().build());
	}
	
		@GetMapping("/titulo/{titulo}")
		public ResponseEntity<List<Produto>> getByTitulo (@PathVariable String titulo){
			return ResponseEntity.ok(produtoRepository.finAllByTituloContainingIgnoreCase(titulo));	
	}
		
		@GetMapping("/preco_maior/{preco}")
		public ResponseEntity<List<Produto>> getPrecoMaiorIgualQue(@PathVariable BigDecimal preco){ 
			return ResponseEntity.ok(produtoRepository.findByPrecoGreaterThanEqualOrderByPreco(preco));
		}
		
			 
		@GetMapping("/preco_menor/{preco}")
		public ResponseEntity<List<Produto>> getPrecoMenorIgualQue(@PathVariable BigDecimal preco){ 
			return ResponseEntity.ok(produtoRepository.findByPrecoLessThanEqualOrderByPreco(preco));
		}
		
		
		@PostMapping
		public ResponseEntity<Produto>postProduto(@Valid @RequestBody Produto produto){ // posso substituir o inicio por void?
			if (categoriaRepository.existsById(produto.getCategoria().getId()))
				return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
	}
		@PutMapping
		public ResponseEntity<Produto>putProduto (@Valid @RequestBody Produto produto){
			if (produtoRepository.existsById(produto.getId())){
				if(categoriaRepository.existsById(produto.getCategoria().getId()))
					return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		@DeleteMapping("/{id}")
		public ResponseEntity<?> deletePostagem(@PathVariable Long id){
			return produtoRepository.findById(id)
					.map(obj -> {
						produtoRepository.deleteById(id);
						return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
					})
					.orElse(ResponseEntity.notFound().build());
					
		}
		
		
	}


		
			
