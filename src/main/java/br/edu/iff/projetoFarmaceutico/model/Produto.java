package br.edu.iff.projetoFarmaceutico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

@Entity
public class Produto implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    @Column(nullable = false, length = 30)
    @NotBlank(message = "Nome do produto é obrigatório.")
    @Size(min = 2, max = 30, message = "Nome do produto deve ter entre 2 e 30 caracteres.")
    private String nome;
    @Column (nullable = false, scale = 2, precision = 2)
    @Digits (integer = 5, fraction = 1, message = "Dose inválida.")
    private Double dose;//=1.0 
    //Não achei uma solução tecnicamente correta para deixar esse valor 1.0 default como definido nas restrições;
    @Column (nullable = false, length = 100)
    @NotBlank (message = "Empresa deve ser preenchida.")
    @Length(min = 2,message = "Nome de empresa inválido.")
    private String empresa;
    @Column (nullable = false, scale = 2, precision = 2)
    @Digits (integer = 5, fraction = 2, message = "Valor inválido.")
    @NotBlank(message="Digite o preço.")
    private Double preco;
    
    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<Pedido> pedidos = new ArrayList();

    public Produto() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getDose() {
        return dose;
    }

    public void setDose(Double dose) {
        this.dose = dose;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
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
        hash = 41 * hash + this.codigo;
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
        final Produto other = (Produto) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }
   
    
}
