package br.edu.iff.projetoFarmaceutico.controller.view;

import br.edu.iff.projetoFarmaceutico.model.Representante;
import br.edu.iff.projetoFarmaceutico.repository.PermissaoRepository;
import br.edu.iff.projetoFarmaceutico.service.RepresentanteService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/representantes")
public class RepresentanteViewController {

    @Autowired
    private RepresentanteService service;
    @Autowired
    private PermissaoRepository permissaoRepo;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("representantes", service.findALL());
        return "representantes";
    }

    @GetMapping(path = "/representante")
    public String cadastro(Model model) {
        model.addAttribute("representante", new Representante());
        model.addAttribute("permissoes", permissaoRepo.findAll());
        return "formRepresentante";
    }

    @PostMapping(path = "/representante")
    public String save(@Valid @ModelAttribute Representante representante, BindingResult result, @RequestParam("confirmarSenha") String confirmarSenha, Model model) {

        //Valores a serem retornados
        model.addAttribute("permissoes", permissaoRepo.findAll());
        

        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formRepresentante";
        }

        if (!representante.getSenha().equals(confirmarSenha)) {
            model.addAttribute("msgErros", new ObjectError("representante", "Campos Senha e Confirmar Senha devem ser iguais."));
            return "formRepresentante";
        }

        representante.setIdRepresentante(null);
        try {
            service.save(representante);
            model.addAttribute("msgSucesso", "Representante cadastrado com sucesso.");
            model.addAttribute("representante", new Representante());
            return "formRepresentante";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("representante", e.getMessage()));
            return "formRepresentante";
        }
    }

    @GetMapping(path = "/representante/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {
        model.addAttribute("representante", service.findById(id));
        model.addAttribute("permissoes", permissaoRepo.findAll());
        return "formRepresentante";
    }

    @PostMapping(path = "/representante/{id}")
    public String update(@Valid @ModelAttribute Representante representante, BindingResult result, @PathVariable("id") Long id, Model model) {

        //Valores a serem retornados
        model.addAttribute("permissoes", permissaoRepo.findAll());

        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("senha")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formRepresentante";
        }
        representante.setIdRepresentante(id);
        try {
            service.update(representante, "", "", "");
            model.addAttribute("msgSucesso", "Representante atualizado com sucesso.");
            model.addAttribute("representante", representante);
            return "formRepresentante";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("Representante", e.getMessage()));
            return "formRepresentante";
        }
    }

    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/representantes";
    }

    //------------ MEUS DADOS  ----------------
    @GetMapping(path = "/meusdados")
    public String getMeusDados(@AuthenticationPrincipal User user, Model model) {
        Representante representante = service.findByEmail(user.getUsername());
        model.addAttribute("representante", representante);
        return "formMeusDados";
    }

    @PostMapping(path = "/meusdados")
    public String updateMeusDados(
            @Valid @ModelAttribute Representante representante,
            BindingResult result,
            @AuthenticationPrincipal User user,
            @RequestParam("senhaAtual") String senhaAtual,
            @RequestParam("novaSenha") String novaSenha,
            @RequestParam("confirmarNovaSenha") String confirmarNovaSenha,
            Model model) {

        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("senha") && !fe.getField().equals("permissoes")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formMeusDados";
        }
        
        Representante representanteBD = service.findByEmail(user.getUsername());
        if(!representanteBD.getIdRepresentante().equals(representante.getIdRepresentante())){
            throw new RuntimeException("Acesso negado.");
        }
        try {
            representante.setPermissoes(representanteBD.getPermissoes());
            service.update(representante, senhaAtual, novaSenha, confirmarNovaSenha);
            model.addAttribute("msgSucesso", "Representante atualizado com sucesso.");
            model.addAttribute("representante", representante);
            return "formMeusDados";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("representante", e.getMessage()));
            return "formMeusDados";
        }
    }
    }
