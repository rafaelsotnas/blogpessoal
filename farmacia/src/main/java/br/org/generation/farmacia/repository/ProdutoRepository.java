package br.org.generation.farmacia.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.farmacia.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	public List<Produto> findAllByNomeContainingIgnoreCase(@Valid String nome);
	public List<Produto> findAllByPrecoBetween(@Valid BigDecimal start, @Valid BigDecimal end);
}
