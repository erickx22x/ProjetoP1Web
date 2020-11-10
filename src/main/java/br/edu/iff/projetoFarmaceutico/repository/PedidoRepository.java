package br.edu.iff.projetoFarmaceutico.repository;

import br.edu.iff.projetoFarmaceutico.model.Pedido;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
    @Query("SELECT DISTINCT(p) FROM Pedido p WHERE p.representante.idRepresentante =:idRepresentante AND p.cliente.idCliente =:idCliente")
    public List<Pedido> findByRepresentanteIdAndClienteId(@Param("idRepresentante")Long idRepresentante,@Param("idCliente")Long idCliente, Pageable page);
    @Query("SELECT DISTINCT(p) FROM Pedido p WHERE p.representante.idRepresentante =:idRepresentante")
    public List<Pedido> findByRepresentanteId(@Param("idRepresentante")Long idRepresentante, Pageable page);
    @Query("SELECT DISTINCT(p) FROM Pedido p WHERE p.cliente.idCliente =:idCliente")
    public List<Pedido> findByClienteId(@Param("idCliente")Long idCliente, Pageable page);
    @Query("SELECT DISTINCT(p) FROM Pedido p WHERE p.produto.codigo =:codigoProduto")
    public List<Pedido> findByProdutoId(@Param("codigoProduto")Long codigoProduto, Pageable page);

}
