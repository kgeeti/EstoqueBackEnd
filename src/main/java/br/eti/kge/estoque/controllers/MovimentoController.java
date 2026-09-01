package br.eti.kge.estoque.controllers;

import br.eti.kge.estoque.dtos.MovimentoDTO;
import br.eti.kge.estoque.entities.Movimento;
import br.eti.kge.estoque.repositories.MovimentoRepository;
import br.eti.kge.estoque.services.MovimentoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author KGe
 */
@RestController
@RequestMapping("/movtos")

public class MovimentoController {

    @Autowired
    private MovimentoService movtoService;

    /**
     * Atualiza saldo do estoque com base no id de produto e quantidade
     * @param movtoDTO
     * @return 
     */
    @PostMapping
    public ResponseEntity<Movimento> atualizaSaldo(@RequestBody @Valid MovimentoDTO movtoDTO) {

        Movimento newMovto = movtoService.atualizaSaldo(
                movtoDTO.getIdProduto(),
                movtoDTO.getQuantidade());

        return ResponseEntity.status(HttpStatus.CREATED).body(newMovto);
    }
    
    @GetMapping("/relatorio/saidas")
    public ResponseEntity<List<Movimento>> relatorioSaidasDecrescente() {
        List<Movimento> relatorioSaidas = movtoService.relatorioSaidasDesc();
        
        if (relatorioSaidas.isEmpty()) {
            
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(relatorioSaidas);
    }

}
