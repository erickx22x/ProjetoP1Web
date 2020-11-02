package br.edu.iff.projetoFarmaceutico.service;

import br.edu.iff.projetoFarmaceutico.model.Produto;
import br.edu.iff.projetoFarmaceutico.repository.ProdutoRepository;
//import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repo;
    
    public List<Produto> findALL(){
        return repo.findAll();
    }
    
    public Produto findById(Long id){//atenção aqui
        Optional<Produto> result = repo.findById(id);
        if(result.isEmpty()){
            throw new RuntimeException("Produto não encontrado.");
        }
        return result.get();
    }
    
    public Produto save(Produto c){
       try{
         return repo.save(c);
       }catch(Exception e){
         throw new RuntimeException("Falha ao salvar produto.");
       }
    }
    
    public Produto update(Produto c){
        Produto obj = findById(c.getCodigo());
       try{
         return repo.save(c);
       }catch(Exception e){
         throw new RuntimeException("Falha ao salvar cliente.");
       }       
    }
}
