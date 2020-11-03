package br.edu.iff.projetoFarmaceutico.repository;

import br.edu.iff.projetoFarmaceutico.model.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    @Query("SELECT Distinct(c) FROM Cliente c WHERE c.cnpj =:cnpj OR c.nome = :nome")
    public List<Cliente> findByCnpjOrName(@Param("cnpj")String cnpj, @Param ("nome")String nome);
}
