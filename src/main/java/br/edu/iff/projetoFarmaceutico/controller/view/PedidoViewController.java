package br.edu.iff.projetoFarmaceutico.controller.view;

import br.edu.iff.projetoFarmaceutico.model.Pedido;
import br.edu.iff.projetoFarmaceutico.service.ClienteService;
import br.edu.iff.projetoFarmaceutico.service.PedidoService;
import br.edu.iff.projetoFarmaceutico.service.ProdutoService;
import br.edu.iff.projetoFarmaceutico.service.RepresentanteService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/pedidos")
public class PedidoViewController {

    @Autowired
    private PedidoService service;
    @Autowired
    private RepresentanteService representanteService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("pedidos", service.findALL());
        return "pedidos";
    }

    @GetMapping(path = "/pedido")
    public String cadastro(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("representantes", representanteService.findALL());
        model.addAttribute("clientes", clienteService.findALL());
        model.addAttribute("produtos", produtoService.findALL());

        return "formPedido";
    }

    @PostMapping(path = "/pedido")
    public String save(@Valid @ModelAttribute Pedido pedido, BindingResult result, Model model) {
        
        model.addAttribute("representantes", representanteService.findALL());
        model.addAttribute("clientes", clienteService.findALL());
        model.addAttribute("produtos", produtoService.findALL());
        
        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("dataPedido")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formPedido";
        }    
       
        pedido.setIdPedido(null);
        
        try {
            service.save(pedido);
            model.addAttribute("msgSucesso", "Pedido cadastrado com sucesso.");
            model.addAttribute("pedido", new Pedido());
            return "formPedido";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("Pedido", e.getMessage()));
            return "formPedido";
        }
    }
    
    @GetMapping(path = "/pedido/{id}")
    public String alterar(@PathVariable("id")Long id, Model model) {
        model.addAttribute("pedido",service.findById(id));
        model.addAttribute("representantes", representanteService.findALL());
        model.addAttribute("clientes", clienteService.findALL());
        model.addAttribute("produtos", produtoService.findALL());
        return "formPedido";
    }
    
    @PostMapping(path = "/pedido/{id}")
    public String update(@Valid @ModelAttribute Pedido pedido, BindingResult result, @PathVariable("id")Long id, Model model) {

        model.addAttribute("representantes", representanteService.findALL());
        model.addAttribute("clientes", clienteService.findALL());
        model.addAttribute("produtos", produtoService.findALL());
                
        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("dataPedido")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formPedido";
        }       
            
        pedido.setIdPedido(id);
        try {
            service.update(pedido);
            model.addAttribute("msgSucesso", "Pedido atualizado com sucesso.");
            model.addAttribute("pedido", pedido);
            return "formProduto";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("Pedido", e.getMessage()));
            return "formPedido";
        }
    }
    
    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id")Long id) {
        service.delete(id);
        return "redirect:/pedidos";
    }
}
