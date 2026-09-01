package br.eti.kge.estoque.repositories;

import br.eti.kge.estoque.dtos.CategoriaValorTotalDTO;
import br.eti.kge.estoque.dtos.RelatorioEstoqueDTO;
import br.eti.kge.estoque.entities.Movimento;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KGe
 */
@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long> {

    List<Movimento> findByQtdLessThanOrderByDataMovto(Double qtd);

    @Query("SELECT m "
            + "FROM Movimento m WHERE m.qtd < 0 "
            + "ORDER BY m.dataMovto DESC")
    List<Movimento> relatorioSaidasDesc();


    // resumo produto, saidas, entradas, periodo
    // Usando JPA (Comportamento estranho que duplica o registro de estoque contabilizando
    // um registro com todas as entradas e outro registro com todas as saídas.
    
    @Query("SELECT new br.eti.kge.estoque.dtos.RelatorioEstoqueDTO("
            + "p.id, p.nome, p.valorUnitario, "
            + "SUM(CASE WHEN m.qtd > 0 THEN m.qtd ELSE 0.0 END), "
            + "SUM(CASE WHEN m.qtd < 0 THEN -m.qtd ELSE 0.0 END)) "
            + "FROM Movimento m JOIN m.produto p "
            + "WHERE (:dataInicio IS NULL OR m.dataMovto >= :dataInicio) "
            + "AND (:dataFim IS NULL OR m.dataMovto <= :dataFim) "
            + "GROUP BY p.id "
            + "ORDER BY p.nome")
    List<RelatorioEstoqueDTO> findRelatorioEstoqueViaJPQL(@Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim);

    
    // Usando SQL Nativa MySQL
    @Query(value =
        "SELECT p.id, p.nome, p.valor_unitario, " +
        "       SUM(CASE WHEN m.qtd > 0 THEN m.qtd ELSE 0 END) as entradas, " +
        "       SUM(CASE WHEN m.qtd < 0 THEN -m.qtd ELSE 0 END) as saidas " +
        "FROM movimento m " +
        "JOIN produto p ON p.id = m.id_produto " +
        "WHERE (:dataInicio IS NULL OR m.data_movto >= :dataInicio) " +
        "AND (:dataFim IS NULL OR m.data_movto <= :dataFim) " +
        "GROUP BY p.id " +
        "ORDER BY p.id",
        nativeQuery = true)
        List<Object[]> findRelatorioEstoqueNative(@Param("dataInicio") LocalDateTime dataInicio,
                                               @Param("dataFim") LocalDateTime dataFim);
    }
