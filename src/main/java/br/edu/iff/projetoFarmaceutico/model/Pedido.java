package br.edu.iff.projetoFarmaceutico.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "Id não pode ser vazio.")
    private int idPedido;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message="Preencha a data!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar dataPedido;
    @Column (nullable = false)    
    @NotBlank(message = "Quantidade de produtos não pode estar em branco.")
    @Min(value = 1,message = "A quantidade mínima de produtos é 1.")
    @Digits (integer = 5, fraction = 0, message = "O número deve ser inteiro e ter no máximo 5 dígitos.")
    private int quantProdutos;
    
    
    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message="Campo obrigatório.")
    @Size(max = 1, min = 1, message = "Deve haver apenas um representante para esse pedido.")
    private Representante representante;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    @Size(max = 1, min = 1, message = "Deve haver apenas um cliente para esse pedido.")
    @NotNull(message="Campo obrigatório.")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    @Size(max = 1, min = 1, message = "Deve haver apenas um produto para esse pedido.")
    @NotNull(message="Campo obrigatório.")
    private Produto produto;

    public Pedido() {
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Calendar getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Calendar dataPedido) {
        this.dataPedido = dataPedido;
    }

    public int getQuantProdutos() {
        return quantProdutos;
    }

    public void setQuantProdutos(int quantProdutos) {
        this.quantProdutos = quantProdutos;
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
   
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.idPedido;
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
        final Pedido other = (Pedido) obj;
        if (this.idPedido != other.idPedido) {
            return false;
        }
        return true;
    }

    
   
}
