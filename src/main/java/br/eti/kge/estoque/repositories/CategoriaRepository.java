package br.eti.kge.estoque.repositories;

import br.eti.kge.estoque.dtos.CategoriaValorTotalDTO;
import br.eti.kge.estoque.entities.Categoria;
import br.eti.kge.estoque.projections.CategoriaValorTotalProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KGe
 */
@Repository
@RepositoryRestResource(path = "categorias_hateoas")
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    /**
     * Query Nativa (MySQL) para uso com Projections (interface)
     * Necessário a camada de serviço para remapear em Lista
     * Quando nativeQuery = true retorna uma "projection".
     * 
     * Na vida real ou usa Nativa ou usa JPQL
     * 
     * Vantagens: Linguagem direta do banco - permite tunning de performance.
     * Desvantagens: Mudou o banco, muda a query.
     * 
     * @return Lista de Projections.
     */
    
    @Query(value = "SELECT c.categoria AS categoria, SUM(p.saldo * p.valor_unitario) AS valorTotal " +
                   "FROM categoria c " +
                   "INNER JOIN produto p ON p.id_categoria = c.id " +
                   "GROUP BY c.id, c.categoria", 
           nativeQuery = true)
    List<CategoriaValorTotalProjection> calcularValorTotalPorCategoriaViaSqlNativo();
 
    
    /**
     * Query JPQL (Java Persistence Query Language) para uso com DTO
     * Perceba que ela já agrega o construtor do DTO na própria Query retornando a lista pronta.
     * 
     * * Na vida real ou usa Nativa ou usa JPQL
     * 
     * Vantagens: Serve para qualquer banco.
     *            Já retorna a lista pronta, sem necessidade de camada service para mapeamento.
     * Desvantagens: Necessário mapeamento @ManyToOne ou @OneToMany via classe.
     *               Não permite tunning de performance.
     * 
     * @return 
     */
    @Query("SELECT NEW br.eti.kge.estoque.dtos.CategoriaValorTotalDTO(c.categoria, SUM(p.saldo * p.valorUnitario)) " +
           "FROM Categoria c JOIN Produto p ON p.categoria = c " +
           "GROUP BY c.id, c.categoria")
    List<CategoriaValorTotalDTO> calcularValorTotalPorCategoriaViaJPQL();
}

