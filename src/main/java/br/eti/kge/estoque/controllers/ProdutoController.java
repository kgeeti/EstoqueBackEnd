package br.eti.kge.estoque.controllers;

import br.eti.kge.estoque.dtos.RelatorioEstoqueDTO;
import br.eti.kge.estoque.entities.Produto;
import br.eti.kge.estoque.services.ProdutoService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author KGe
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping

    public ResponseEntity<List<Produto>> listar() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        return produto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
        Produto novoProduto = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            produtoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    /**
     * devolve um relatório de movimentações com base em data de inicio e data fim
     * 
     * informar a dataInicio e dataFim no formato AAAA-MM-DD na request QueryParams.
     * 
     * @param dataInicio
     * @param dataFim
     * @return 
     */
    @GetMapping("/relatorio/estoque")
    public ResponseEntity<List<RelatorioEstoqueDTO>> relatorio(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dataInicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dataFim) {

        LocalDateTime inicio = (dataInicio != null) ? dataInicio.atStartOfDay() : null;
        LocalDateTime fim = (dataFim != null) ? dataFim.atTime(23, 59, 59) : null;

        List<RelatorioEstoqueDTO> relatorio = produtoService.gerarRelatorio(inicio, fim);
        
        return ResponseEntity.ok(relatorio);
    }
}
