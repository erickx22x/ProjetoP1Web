package br.edu.iff.projetoFarmaceutico.model;

import br.edu.iff.projetoFarmaceutico.annotation.EmailValidation;
import br.edu.iff.projetoFarmaceutico.annotation.PasswordValidation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;


@Entity
@JsonIgnoreProperties(value = "senha", allowGetters = false, allowSetters = true)
public class Representante implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRepresentante;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Senha obrigatória.")
    @Length(min = 8,message = "Senha deve ter no mínimo 8 caracteres.")
    @PasswordValidation(message="Senha inválida.")
    private String senha;
    @Column(nullable = false, length = 30)
    @NotBlank(message = "Nome do representante é obrigatório.")
    @Length(min = 2, max = 30, message = "Nome do representante deve ter entre 3 e 30 caracteres.")
    private String nome;
    @Column(nullable = false, length = 55, unique = true, updatable = false)
    @EmailValidation(message = "Email inválido.")
    @NotBlank(message="Email obrigatório.")
    @Length(max=55)
    private String email;
    
    @JsonIgnore
    @OneToMany(mappedBy = "representante")
    private List<Pedido> pedidos = new ArrayList<>();

    public Representante() {
    }
    
    public Long getIdRepresentante() {
        return idRepresentante;
    }

    public void setIdRepresentante(Long idRepresentante) {
        this.idRepresentante = idRepresentante;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idRepresentante);
        return hash;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Representante other = (Representante) obj;
        if (this.idRepresentante != other.idRepresentante) {
            return false;
        }
        return true;
    }
    
    

}
           
    
    

