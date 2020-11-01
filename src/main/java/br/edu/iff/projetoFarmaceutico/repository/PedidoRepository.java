package br.edu.iff.projetoFarmaceutico.repository;

import br.edu.iff.projetoFarmaceutico.model.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    public Pedido findById(String idPedido);
        
    @Query("SELECT DISTINCT(p) FROM Representante r Join r.pedidos p WHERE r.idRepresentante =:idRepresentante")
    public List<Pedido> findByRepresentanteId(@Param("idRepresentante")Long idRepresentante);
    @Query("SELECT DISTINCT(p) FROM Cliente c Join c.pedidos p WHERE c.idCliente =:idCliente")
    public List<Pedido> findByClienteId(@Param("idCliente")Long idCliente);
}
