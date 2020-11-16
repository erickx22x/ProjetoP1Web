package br.edu.iff.projetoFarmaceutico.service;

import br.edu.iff.projetoFarmaceutico.exception.NotFoundException;
import br.edu.iff.projetoFarmaceutico.model.Representante;
import br.edu.iff.projetoFarmaceutico.repository.RepresentanteRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RepresentanteService {
    @Autowired
    private RepresentanteRepository repo;
    
    public List<Representante> findALL(int page, int size){
        Pageable p = PageRequest.of(page, size);       
        return repo.findAll(p).toList();
    }  
    
    public List<Representante> findALL(){
        return repo.findAll();
    }
    
    public Representante findById(Long id){
        Optional<Representante> result = repo.findById(id);
        if(result.isEmpty()){
            throw new NotFoundException("Representante não encontrado.");
        }
        return result.get();
    }
    
    public Representante save(Representante r){
      
        verificaEmailCadastrado(r.getEmail());
        try{
            return repo.save(r);      
        }catch(Exception e){
            throw new RuntimeException("Falha ao salvar representante");
        }            
       
    }
    
    public Representante update(Representante r){
       Representante obj = findById(r.getIdRepresentante());
       try{
         r.setEmail(obj.getEmail());
         r.setSenha(obj.getSenha());
         return repo.save(r);
       }catch(Exception e){
          Throwable t = e;
            while (t.getCause()!=null){
                t = t.getCause();
                if(t instanceof ConstraintViolationException){
                    throw((ConstraintViolationException)t);
                }
            }
         throw new RuntimeException("Falha ao atualizar representante.");
       }       
    }
    
    public void delete (Long id){
        Representante obj = findById(id);
        verificaExclusaoRepresentanteComPedido(obj);
        try{
            repo.delete(obj);
        }catch(Exception e){
            throw new RuntimeException("Falha ao deletar representante.");
        }        
    }
    
    private void verificaEmailCadastrado(String email){
        Representante result = repo.findByEmail(email);
        if(result!=null){
            throw new RuntimeException("Email já cadastrado.");
        }
    }
    
    private void verificaExclusaoRepresentanteComPedido(Representante r){
        
        if(!r.getPedidos().isEmpty()){
            throw new RuntimeException("Não é possível excluir representante pois ele ainda possui pedidos.");
        }
    }
    
    
}
