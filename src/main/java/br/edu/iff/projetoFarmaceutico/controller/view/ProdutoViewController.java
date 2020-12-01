package br.edu.iff.projetoFarmaceutico.controller.view;

import br.edu.iff.projetoFarmaceutico.model.Produto;
import br.edu.iff.projetoFarmaceutico.service.ProdutoService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/produtos")
public class ProdutoViewController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("produtos", service.findALL());
        return "produtos";
    }

    @GetMapping(path = "/produto")
    public String cadastro(Model model) {
        model.addAttribute("produto", new Produto());
        return "formProduto";
    }

    @PostMapping(path = "/produto")
    public String save(@Valid @ModelAttribute Produto produto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formProduto";
        }
        
        produto.setCodigo(null);
        try {
            service.save(produto);
            model.addAttribute("msgSucesso", "Produto cadastrado com sucesso.");
            model.addAttribute("produto", new Produto());
            return "formProduto";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("Produto", e.getMessage()));
            return "formProduto";
        }
    }
    
    @GetMapping(path = "/produto/{codigo}")
    public String alterar(@PathVariable("codigo")Long id, Model model) {
        model.addAttribute("produto",service.findById(id));
        return "formProduto";
    }
    
    @PostMapping(path = "/produto/{codigo}")
    public String update(@Valid @ModelAttribute Produto produto, BindingResult result, @PathVariable("codigo")Long id, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formProduto";
        } 
        produto.setCodigo(id);
        try {
            service.update(produto);
            model.addAttribute("msgSucesso", "Produto atualizado com sucesso.");
            model.addAttribute("produto", produto);
            return "formProduto";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("produto", e.getMessage()));
            return "formProduto";
        }
    }
    
    @GetMapping(path = "/{codigo}/deletar")
    public String deletar(@PathVariable("codigo")Long id) {
        service.delete(id);
        return "redirect:/produtos";
    }
}
