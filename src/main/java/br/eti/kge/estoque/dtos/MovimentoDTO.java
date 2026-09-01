package br.eti.kge.estoque.dtos;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author KGe
 */
public class MovimentoDTO {
    
    @NotNull
    Long idProduto;
    
    @NotNull
    Double quantidade;

    public MovimentoDTO() {
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long id_produto) {
        this.idProduto = id_produto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double qtd) {
        this.quantidade = qtd;
    }
    
    
    
    
}
