package br.edu.iff.projetoFarmaceutico.controller;

import br.edu.iff.projetoFarmaceutico.model.Pedido;
import br.edu.iff.projetoFarmaceutico.service.PedidoService;
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
@RequestMapping(path="/apirest/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService service;
    
    
   @GetMapping
    public ResponseEntity getAll(
        @RequestParam(name = "page", defaultValue = "0", required = false)int page,
        @RequestParam(name = "size", defaultValue = "10", required = false)int size,
        @RequestParam(name = "representanteId", defaultValue = "0", required = false)Long representanteId,
        @RequestParam(name = "clienteId", defaultValue = "0", required = false)Long clienteId,
        @RequestParam(name = "produtoCodigo", defaultValue = "0", required = false)Long produtoCodigo){
        
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(page, size, representanteId, clienteId, produtoCodigo));
    }
    
    @GetMapping(path="/{id}")
    public ResponseEntity getOne(@PathVariable("id")Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody Pedido pedido){
        pedido.setIdPedido(null);
        service.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }
    
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id")Long id, @RequestBody Pedido pedido){
        pedido.setIdPedido(id);
        service.update(pedido);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable ("id")Long id){
       service.delete(id);
       return ResponseEntity.ok().build();
    }
    
}
