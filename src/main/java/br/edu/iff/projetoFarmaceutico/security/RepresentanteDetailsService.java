package br.edu.iff.projetoFarmaceutico.security;

import br.edu.iff.projetoFarmaceutico.model.Permissao;
import br.edu.iff.projetoFarmaceutico.model.Representante;
import br.edu.iff.projetoFarmaceutico.repository.RepresentanteRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RepresentanteDetailsService implements UserDetailsService {

    @Autowired
    private RepresentanteRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Representante representante = repo.findByEmail(email);
        if(representante ==null){
            throw new UsernameNotFoundException("Funcionário não encontra com esse email:"+email);
        }
        return new User(representante.getEmail(), representante.getSenha(), getAuthorities(representante.getPermissoes()));
    }

    private List<GrantedAuthority> getAuthorities(List<Permissao> lista) {
        List<GrantedAuthority> l = new ArrayList<>();
        for (Permissao p : lista) {
            l.add(new SimpleGrantedAuthority("ROLE_"+p.getNome()));
        }
        return l;
    }
    
}
