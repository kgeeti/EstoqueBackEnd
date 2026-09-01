package br.eti.kge.estoque.services;

import br.eti.kge.estoque.dtos.RelatorioEstoqueDTO;
import br.eti.kge.estoque.entities.Produto;
import br.eti.kge.estoque.repositories.MovimentoRepository;
import br.eti.kge.estoque.repositories.ProdutoRepository;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author KGe
 */
@Service
public class ProdutoService {

    @Autowired
    private MovimentoRepository movimentoRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id).map(produto -> {
            produto.setNome(produtoAtualizado.getNome());
            produto.setCategoria(produtoAtualizado.getCategoria());
            produto.setSaldo(produtoAtualizado.getSaldo());
            produto.setValorUnitario(produtoAtualizado.getValorUnitario());
            return produtoRepository.save(produto);
        }).orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
    }

    public void deletar(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Produto não encontrado com o ID: " + id);
        }
    }
    
    public List<RelatorioEstoqueDTO> gerarRelatorio(LocalDateTime dataInicio, LocalDateTime dataFim) {

        // usando a Opção A (JPQL)
        // return movimentoRepository.findRelatorioEstoqueViaJPQL(dataInicio, dataFim);
        
        
        List<Object[]> resultado = movimentoRepository.findRelatorioEstoqueNative(dataInicio, dataFim);
        List<RelatorioEstoqueDTO> relatorio = new ArrayList<>();

        for (Object[] linha : resultado) {
            RelatorioEstoqueDTO dto = new RelatorioEstoqueDTO();
            dto.setProdutoId(((Number) linha[0]).longValue());
            dto.setNomeProduto((String) linha[1]);
            dto.setValorUnitario((BigDecimal) linha[2]);
            dto.setTotalEntradas(((Number) linha[3]).doubleValue());
            dto.setTotalSaidas(((Number) linha[4]).doubleValue());
            relatorio.add(dto);
        }

        return relatorio;
        
    }
}

