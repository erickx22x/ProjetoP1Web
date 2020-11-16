package br.edu.iff.projetoFarmaceutico.service;

import br.edu.iff.projetoFarmaceutico.exception.NotFoundException;
import br.edu.iff.projetoFarmaceutico.model.Produto;
import br.edu.iff.projetoFarmaceutico.repository.ProdutoRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    public List<Produto> findALL(int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }

    public List<Produto> findALL() {
        return repo.findAll();
    }

    public Produto findById(Long id) {
        Optional<Produto> result = repo.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Produto não encontrado.");
        }
        return result.get();
    }

    public Produto save(Produto c) {
        try {
            return repo.save(c);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar produto.");
        }
    }

    public Produto update(Produto c) {
        Produto obj = findById(c.getCodigo());
        try {
            return repo.save(c);
        } catch (Exception e) {
            Throwable t = e;
            while (t.getCause()!=null){
                t = t.getCause();
                if(t instanceof ConstraintViolationException){
                    throw((ConstraintViolationException)t);
                }
            }
            throw new RuntimeException("Falha ao atualizar produto.");
        }
    }

    public void delete(Long id) {
        Produto obj = findById(id);
        verificaExclusaoProdutoComPedido(obj);
        try {
            repo.delete(obj);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao deletar o produto");
        }
    }

    private void verificaExclusaoProdutoComPedido(Produto p) {

        if (!p.getPedidos().isEmpty()) {
            throw new RuntimeException("Não é possível excluir produto pois ele ainda está em algum(uns) pedido(s).");
        }
    }

}
