package br.edu.iff.projetoFarmaceutico.controller.apirest;

import br.edu.iff.projetoFarmaceutico.model.Representante;
import br.edu.iff.projetoFarmaceutico.service.RepresentanteService;
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
@RequestMapping(path = "/apirest/representantes")
public class RepresentanteController {
    @Autowired
    private RepresentanteService service;
    
    @GetMapping
    public ResponseEntity getAll(
        @RequestParam(name = "page", defaultValue = "0", required = false)int page,
        @RequestParam(name = "size", defaultValue = "10", required = false)int size){
        return ResponseEntity.status(HttpStatus.OK).body(service.findALL(page,size));
    }
    
    @GetMapping (path = "/{id}")
    public ResponseEntity getOne(@PathVariable("id")Long id){
       return ResponseEntity.ok(service.findById(id));
    }
    
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody Representante representante){
        representante.setIdRepresentante(null);
        service.save(representante);
        return ResponseEntity.status(HttpStatus.CREATED).body(representante);
    }
    
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id")Long id, @Valid @RequestBody Representante representante){
        representante.setIdRepresentante(id);
        service.update(representante, "","","");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping(path = "/{id}/alterarSenha")
    public ResponseEntity alterarSenha(@PathVariable("id") Long id,
            @RequestParam(name = "senhaAtual", defaultValue = "", required = true) String senhaAtual,
            @RequestParam(name = "novaSenha", defaultValue = "", required = true) String novaSenha,
            @RequestParam(name = "confirmarSenha", defaultValue = "", required = true) String confirmarSenha){
        
        Representante r = service.findById(id);
        service.update(r, senhaAtual, novaSenha, confirmarSenha);
        return ResponseEntity.ok().build();
    }
}
