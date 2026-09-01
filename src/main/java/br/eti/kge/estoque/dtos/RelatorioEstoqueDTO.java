package br.eti.kge.estoque.dtos;

import java.math.BigDecimal;

/**
 * Representa os dados do relatório de estoque - resumo
 * @author KGe
 */
public class RelatorioEstoqueDTO {

    private Long produtoId;
    private String nomeProduto;
    private BigDecimal valorUnitario;
    private Double totalEntradas;
    private Double totalSaidas;

    public RelatorioEstoqueDTO() {
    }

    public RelatorioEstoqueDTO(Long produtoId, String nomeProduto, BigDecimal valorUnitario,
            Double totalEntradas, Double totalSaidas) {
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.valorUnitario = valorUnitario;
        this.totalEntradas = totalEntradas;
        this.totalSaidas = totalSaidas;
    }

    // getters e setters
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    
    public Double getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(Double totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public Double getTotalSaidas() {
        return totalSaidas;
    }

    public void setTotalSaidas(Double totalSaidas) {
        this.totalSaidas = totalSaidas;
    }
}
