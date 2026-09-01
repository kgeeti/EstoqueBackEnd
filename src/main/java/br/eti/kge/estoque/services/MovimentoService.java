package br.eti.kge.estoque.services;

import br.eti.kge.estoque.entities.Movimento;
import br.eti.kge.estoque.entities.Produto;
import br.eti.kge.estoque.repositories.MovimentoRepository;
import br.eti.kge.estoque.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author KGe
 */
@Service
public class MovimentoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    MovimentoRepository movtoRepository;

    @Transactional
    public Movimento atualizaSaldo(Long idProduto, Double qtd) {

        Produto produto = produtoRepository
                .findById(idProduto)
                .orElseThrow(()
                        -> new RuntimeException("Produto não encontrado com o ID: " + idProduto)
                );

        /*
        // -------------------------
        // 2. PAUSA FORÇADA: Simula latência no meio da transação
        // -------------------------
        String nomeThread = "Thread_"+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        
        System.out.println("-> [" + nomeThread + "] LEU SALDO ATUAL: " + produto.getSaldo());

            try {
                System.out.printf("-> [%s] Dormindo por 10 segundos...", nomeThread);
                Thread.sleep(10000); // 10 segundos de atraso
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        // -------------------------
        // Fim da latencia forçada
        // -------------------------
        
        */
        
        Movimento movto = new Movimento(produto, qtd);

        movtoRepository.save(movto);

        // A alteração no objeto gerenciado reflete no 
        // banco no commit da transação
        produto.atualizaSaldo(qtd);
        produtoRepository.save(produto);
        
//        System.out.println("-> [" + nomeThread + "] ESCREVENDO NOVO SALDO: " + produto.getSaldo());
        
        
        return movto;
    }

    public List<Movimento> relatorioSaidasDesc() {
        
        return movtoRepository.relatorioSaidasDesc();
        
    }

}
