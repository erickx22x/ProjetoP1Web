package br.edu.iff.projetoFarmaceutico.repository;

import br.edu.iff.projetoFarmaceutico.model.Representante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentanteRepository extends JpaRepository<Representante, Long>{
    public Representante findByEmail (String email);
}
