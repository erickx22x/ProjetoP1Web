package br.edu.iff.projetoFarmaceutico.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
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
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message="Preencha a data!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Calendar dataPedido;
    @Column (nullable = false)    
    @NotNull(message = "Quantidade de produtos não pode estar em branco.")
    @Min(value = 1,message = "A quantidade mínima de produtos é 1.")
    @Digits (integer = 5, fraction = 0, message = "O número deve ser inteiro e ter no máximo 5 dígitos.")
    private int quantProdutos;
    
    
    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message="Campo obrigatório.")
    private Representante representante;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message="Campo obrigatório.")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message="Campo obrigatório.")
    private Produto produto;

    public Pedido() {
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
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
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idPedido);
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
        if (!Objects.equals(this.idPedido, other.idPedido)) {
            return false;
        }
        return true;
    }
    
      
   
}
