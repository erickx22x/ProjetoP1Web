package br.edu.iff.projetoFarmaceutico.service;

import br.edu.iff.projetoFarmaceutico.model.Cliente;
import br.edu.iff.projetoFarmaceutico.repository.ClienteRepository;
//import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;
    
    /*
    public List<Cliente> findALL(int page, int size){
        Pageable p = PageRequest.of(page, size);       
        return repo.findAll(p).toList();
    }
    */
    
    public List<Cliente> findALL(){
        return repo.findAll();
    }
    
    public Cliente findById(Long id){
        Optional<Cliente> result = repo.findById(id);
        if(result.isEmpty()){
            throw new RuntimeException("Cliente não encontrado.");
        }
        return result.get();
    }
    
    public Cliente save(Cliente c){
       try{
         return repo.save(c);
       }catch(Exception e){
         throw new RuntimeException("Falha ao salvar cliente.");
       }
    }
    
    public Cliente update(Cliente c){
        Cliente obj = findById(c.getIdCliente());
       try{
         return repo.save(c);
       }catch(Exception e){
         throw new RuntimeException("Falha ao salvar cliente.");
       }       
    }
}
