package br.edu.iff.projetoFarmaceutico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

@Entity
public class Produto implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @Column(nullable = false, length = 30)
    @NotBlank(message = "Nome do produto é obrigatório.")
    @Length(min = 2, max = 30, message = "Nome do produto deve ter entre 2 e 30 caracteres.")
    private String nome;
    @Column (nullable = false, scale = 2, precision = 2)
    @Digits (integer = 5, fraction = 1, message = "Dose inválida.")
    @Positive (message = "Não coloque valores negativos.")
    private Double dose = 1.0;
    @Column (nullable = false, length = 100)
    @NotBlank (message = "Empresa deve ser preenchida.")
    @Length(min = 2,message = "Nome de empresa inválido.")
    private String empresa;
    @Column (nullable = false, scale = 2, precision = 2)
    @Digits (integer = 5, fraction = 2, message = "Valor inválido.")
    @Positive (message = "Não coloque valores negativos.")
    @NotNull(message="Digite o preço.")
    private Double preco;
    
    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<Pedido> pedidos = new ArrayList<>();

    public Produto() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
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
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.codigo);
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
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
   
    
}
