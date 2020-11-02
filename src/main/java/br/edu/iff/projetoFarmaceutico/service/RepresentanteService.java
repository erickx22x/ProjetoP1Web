package br.edu.iff.projetoFarmaceutico.service;

import br.edu.iff.projetoFarmaceutico.model.Representante;
import br.edu.iff.projetoFarmaceutico.repository.RepresentanteRepository;
//import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RepresentanteService {
    @Autowired
    private RepresentanteRepository repo;
    
    public List<Representante> findALL(){
        return repo.findAll();
    }
    
    public Representante findById(Long id){
        Optional<Representante> result = repo.findById(id);
        if(result.isEmpty()){
            throw new RuntimeException("Representante não encontrado.");
        }
        return result.get();
    }
    
    public Representante save(Representante c){
       try{
         return repo.save(c);
       }catch(Exception e){
         throw new RuntimeException("Falha ao salvar representante.");
       }
    }
    
    public Representante update(Representante c){
        Representante obj = findById(c.getIdRepresentante());
       try{
         return repo.save(c);
       }catch(Exception e){
         throw new RuntimeException("Falha ao salvar representante.");
       }       
    }
}
