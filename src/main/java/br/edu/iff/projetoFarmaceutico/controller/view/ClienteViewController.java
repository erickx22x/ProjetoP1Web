package br.edu.iff.projetoFarmaceutico.controller.view;

import br.edu.iff.projetoFarmaceutico.model.Cliente;
import br.edu.iff.projetoFarmaceutico.service.ClienteService;
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
@RequestMapping(path = "/clientes")
public class ClienteViewController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("clientes", service.findALL());
        return "clientes";
    }

    @GetMapping(path = "/cliente")
    public String cadastro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "formCliente";
    }

    @PostMapping(path = "/cliente")
    public String save(@Valid @ModelAttribute Cliente cliente, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formCliente";
        }

        cliente.setIdCliente(null);
        try {
            service.save(cliente);
            model.addAttribute("msgSucesso", "Cliente cadastrado com sucesso.");
            model.addAttribute("cliente", new Cliente());
            return "formCliente";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("cliente", e.getMessage()));
            return "formCliente";
        }
    }

    @GetMapping(path = "/cliente/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cliente", service.findById(id));
        return "formCliente";
    }

    @PostMapping(path = "/cliente/{id}")
    public String update(@Valid @ModelAttribute Cliente cliente, BindingResult result, @PathVariable("id") Long id, Model model) {

        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("cnpj")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formCliente";
        }
        cliente.setIdCliente(id);
        try {
            service.update(cliente);
            model.addAttribute("msgSucesso", "Cliente atualizado com sucesso.");
            model.addAttribute("cliente", cliente);
            return "formCliente";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("Cliente", e.getMessage()));
            return "formCliente";
        }
    }

    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/clientes";
    }
}
