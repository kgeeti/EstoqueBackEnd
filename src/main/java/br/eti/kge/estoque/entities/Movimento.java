package br.eti.kge.estoque.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 *
 * @author KGe
 */
@Entity
@Table(name = "movimento")
public class Movimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    Produto produto;
    
    @NotNull
    Double qtd;
    
    LocalDateTime dataMovto;

    public Movimento() {
        this.dataMovto = LocalDateTime.now();
    }

    public Movimento(Produto produto, Double qtd) {
        this.produto = produto;
        this.qtd = qtd;
        this.dataMovto = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getQtd() {
        return qtd;
    }

    public void setQtd(Double qtd) {
        this.qtd = qtd;
    }

    public LocalDateTime getDataMovto() {
        return dataMovto;
    }

    public void setDataMovto(LocalDateTime dataMovto) {
        this.dataMovto = dataMovto;
    }
    

}
