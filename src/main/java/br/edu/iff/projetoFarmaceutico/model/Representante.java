package br.edu.iff.projetoFarmaceutico.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class Representante implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int idRepresentante;
    private String senha;
    private String nome;
    private String email;
    
    private List<Pedido> pedidos;

    public Representante() {
    }
    
    public int getIdRepresentante() {
        return idRepresentante;
    }

    public void setIdRepresentante(int idRepresentante) {
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
        int hash = 5;
        hash = 71 * hash + this.idRepresentante;
        hash = 71 * hash + Objects.hashCode(this.nome);
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
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
        
    
    
}
