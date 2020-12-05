package br.edu.iff.projetoFarmaceutico.service;

import br.edu.iff.projetoFarmaceutico.exception.NotFoundException;
import br.edu.iff.projetoFarmaceutico.model.Permissao;
import br.edu.iff.projetoFarmaceutico.model.Representante;
import br.edu.iff.projetoFarmaceutico.repository.RepresentanteRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RepresentanteService {

    @Autowired
    private RepresentanteRepository repo;

    public List<Representante> findALL(int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }

    public List<Representante> findALL() {
        return repo.findAll();
    }

    public Representante findById(Long id) {
        Optional<Representante> result = repo.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Representante não encontrado.");
        }
        return result.get();
    }

    public Representante save(Representante r) {

        verificaEmailCadastrado(r.getEmail());
        //Verifica permissões nulas
        removePermissoesNulas(r);
        try {
            r.setSenha(new BCryptPasswordEncoder().encode(r.getSenha()));
            return repo.save(r);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar representante");
        }

    }

//    public Representante update(Representante r, String senhaAtual, String novaSenha, String confirmarNovaSenha) {
//        Representante obj = findById(r.getIdRepresentante());
//        
//        try {
//            r.setSenha(obj.getSenha());
//            return repo.save(r);
//        } catch (Exception e) {
//            Throwable t = e;
//            while (t.getCause()!=null){
//                t = t.getCause();
//                if(t instanceof ConstraintViolationException){
//                    throw((ConstraintViolationException)t);
//                }
//            }
//            throw new RuntimeException("Falha ao atualizar representante.");
//        }
//    }
    public Representante update(Representante r, String senhaAtual, String novaSenha, String confirmarSenha) {
        Representante obj = findById(r.getIdRepresentante());
        //Verifica permissões nulas
        removePermissoesNulas(r);
        alterarSenha(obj, senhaAtual, novaSenha, confirmarSenha);
        try {
            //r.setEmail(obj.getEmail());
            r.setSenha(obj.getSenha());
            return repo.save(r);
        } catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof ConstraintViolationException) {
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao atualizar representante.");
        }
    }

    public void delete(Long id) {
        Representante obj = findById(id);
        verificaExclusaoRepresentanteComPedido(obj);
        try {
            repo.delete(obj);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao deletar representante.");
        }
    }

    private void verificaEmailCadastrado(String email) {
        Representante result = repo.findByEmail(email);
        if (result != null) {
            throw new RuntimeException("Email já cadastrado.");
        }
    }

    private void alterarSenha(Representante obj, String senhaAtual, String novaSenha, String confirmarSenha) {
        if (!senhaAtual.isBlank() && !novaSenha.isBlank() && !confirmarSenha.isBlank()) {
            if (!senhaAtual.equals(obj.getSenha())) {
                throw new RuntimeException("Senha atual está incorreta.");
            }
            if (!novaSenha.equals(confirmarSenha)) {
                throw new RuntimeException("Nova Senha e Confirmar Nova Senha não conferem.");
            }
            obj.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
        }
    }

    private void verificaExclusaoRepresentanteComPedido(Representante r) {

        if (!r.getPedidos().isEmpty()) {
            throw new RuntimeException("Não é possível excluir representante pois ele ainda possui pedidos.");
        }
    }

    public void removePermissoesNulas(Representante r) {
        r.getPermissoes().removeIf((Permissao p) -> {
            return p.getId() == null;
        });
        if (r.getPermissoes().isEmpty()) {
            throw new RuntimeException("Representante deve conter no mínimo 1 permissão.");
        }
    }

}
