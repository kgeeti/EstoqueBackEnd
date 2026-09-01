package br.eti.kge.estoque.services;

import br.eti.kge.estoque.dtos.CategoriaValorTotalDTO;
import br.eti.kge.estoque.entities.Categoria;
import br.eti.kge.estoque.projections.CategoriaValorTotalProjection;
import br.eti.kge.estoque.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author KGe
 */
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        
        return categoriaRepository.findById(id);
        
        
        
    }

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Long id, Categoria categoriaAtualizada) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setCategoria(categoriaAtualizada.getCategoria());
            return categoriaRepository.save(categoria);
        }).orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));
    }

    public void deletar(Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Categoria não encontrada com o ID: " + id);
        }
    }
    
    /**
     * Carrega o valor total de produtos por categoria utilizando Query Nativa
     * mapeda no CategoriaRepository.
     * 
     * @return 
     */
    public List<CategoriaValorTotalDTO> valorTotalPorCategoriaViaSqlNativo() {
        List<CategoriaValorTotalProjection> resultados = 
                categoriaRepository.calcularValorTotalPorCategoriaViaSqlNativo();
        
        return resultados.stream()
                .map(proj -> new CategoriaValorTotalDTO(proj.getCategoria(), proj.getValorTotal()))
                .collect(Collectors.toList());
    }
    
}

