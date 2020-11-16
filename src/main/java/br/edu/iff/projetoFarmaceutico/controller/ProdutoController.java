package br.edu.iff.projetoFarmaceutico.controller;

import br.edu.iff.projetoFarmaceutico.model.Produto;
import br.edu.iff.projetoFarmaceutico.service.ProdutoService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/apirest/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService service;
    
    @GetMapping
    public ResponseEntity getAll(
        @RequestParam(name = "page", defaultValue = "0", required = false)int page,
        @RequestParam(name = "size", defaultValue = "10", required = false)int size){
        return ResponseEntity.status(HttpStatus.OK).body(service.findALL(page,size));
    }
    
    @GetMapping (path = "/{codigo}")
    public ResponseEntity getOne(@PathVariable("codigo")Long codigo){
       return ResponseEntity.ok(service.findById(codigo));
    }
    
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody Produto produto){
        produto.setCodigo(null);
        service.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }
    
    @PutMapping(path = "/{codigo}")
    public ResponseEntity update(@PathVariable("codigo")Long id,@RequestBody Produto produto){
        produto.setCodigo(id);
        service.update(produto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @DeleteMapping(path = "/{codigo}")
    public ResponseEntity delete(@PathVariable("codigo") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
