package br.edu.iff.projetoFarmaceutico.repository;

import br.edu.iff.projetoFarmaceutico.model.Pedido;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
    public List<Pedido> findByRepresentanteIdAndClienteId(Long idRepresentante, Long idCliente, Pageable page);
    public List<Pedido> findByClienteId(Long idCliente, Pageable page);
    public List<Pedido> findByRepresentanteId(Long idRepresentante, Pageable page);
    public List<Pedido> findByProdutoId(Long codigo, Pageable page);

}
