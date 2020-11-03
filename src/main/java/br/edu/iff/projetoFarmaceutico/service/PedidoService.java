package br.edu.iff.projetoFarmaceutico.service;

import br.edu.iff.projetoFarmaceutico.model.Cliente;
import br.edu.iff.projetoFarmaceutico.model.Pedido;
import br.edu.iff.projetoFarmaceutico.model.Produto;
import br.edu.iff.projetoFarmaceutico.model.Representante;
import br.edu.iff.projetoFarmaceutico.repository.PedidoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public List<Pedido> findALL() {
        return repo.findAll();
    }

    public List<Pedido> findAll(int page, int size, Long representanteId, Long clienteId, Long produtoCodigo) {
        Pageable p = PageRequest.of(page, size);
        if (representanteId != 0 && clienteId != 0) {
            return repo.findByRepresentanteIdAndClienteId(representanteId, clienteId, p);
        } else if (representanteId != 0) {
            return repo.findByRepresentanteId(representanteId, p);
        } else if (clienteId != 0) {
            return repo.findByClienteId(clienteId, p);
        }

        if (produtoCodigo != 0) {
            repo.findByProdutoId(produtoCodigo, p);
        }

        return repo.findAll(p).toList();
    }

    public Pedido findById(Long id) {
        Optional<Pedido> obj = repo.findById(id);
        if (obj.isEmpty()) {
            throw new RuntimeException("Pedido não encontrado.");
        }
        return obj.get();
    }

    public Pedido save(Pedido p) {
        verificaRepresentanteId(p.getRepresentante());
        verificaClienteId(p.getCliente());
        verificaProdutoId(p.getProduto());
        try {
            return repo.save(p);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar Pedido.");
        }
    }

    public Pedido update(Pedido p) {
        Pedido obj = findById(p.getIdPedido());
        try {
            return repo.save(p);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao atualizar pedido.");
        }
    }

    public void delete(Long id) {
        Pedido obj = findById(id);
        try {
            repo.delete(obj);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao deletar pedido.");
        }
    }

    private void verificaRepresentanteId(Representante r) {
        if (r.getIdRepresentante() > 0) {
            throw new RuntimeException("Não é possível salvar pedido pois este id de representante já existe.");
        }
    }

    private void verificaClienteId(Cliente c) {
        if (c.getIdCliente() > 0) {
            throw new RuntimeException("Não é possível salvar pedido pois id de cliente já existe.");
        }
    }

    private void verificaProdutoId(Produto r) {
        if (r.getCodigo() > 0) {
            throw new RuntimeException("Não é possível salvar pedido pois id de produto já existe.");
        }
    }
}
