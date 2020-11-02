package br.edu.iff.projetoFarmaceutico.service;

import br.edu.iff.projetoFarmaceutico.model.Pedido;
import br.edu.iff.projetoFarmaceutico.repository.PedidoRepository;
//import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repo;
    
    
    public List<Pedido> findALL(){
        return repo.findAll();
    }
    
    public Pedido findById(Long id){
        Optional<Pedido> result = repo.findById(id);//<< atenção aqui
        if(result.isEmpty()){
            throw new RuntimeException("Pedido não encontrado.");
        }
        return result.get();
    }
    
    public Pedido save(Pedido c){
       try{
         return repo.save(c);
       }catch(Exception e){
         throw new RuntimeException("Falha ao salvar Pedido.");
       }
    }
    
    public Pedido update(Pedido c){
        Pedido obj = findById(c.getIdPedido());
       try{
         return repo.save(c);
       }catch(Exception e){
         throw new RuntimeException("Falha ao salvar cliente.");
       }       
    }
}
