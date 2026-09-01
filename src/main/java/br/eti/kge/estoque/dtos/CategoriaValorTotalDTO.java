package br.eti.kge.estoque.dtos;

import java.math.BigDecimal;

/**
 * 
 * DTO utilizado como auxiliar de retorno da Query Nativa no cálculo de valor
 * total de produtos por categoria.
 * 
 * @author KGe
 */

public class CategoriaValorTotalDTO {

    private String categoria;
    private BigDecimal valorTotal;

    public CategoriaValorTotalDTO(String categoria, BigDecimal valorTotal) {
        this.categoria = categoria;
        this.valorTotal = valorTotal;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}

