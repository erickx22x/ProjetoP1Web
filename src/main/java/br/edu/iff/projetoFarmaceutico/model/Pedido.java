package br.edu.iff.projetoFarmaceutico.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPedido;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataPedido;
    @Column (nullable = false, scale = 2, precision = 2)    
    private Double quantProdutos;
    
    @JsonManagedReference
    @ManyToOne
    private Representante representante;
    @JsonManagedReference
    @ManyToOne
    private Cliente cliente;
    @JsonManagedReference
    @ManyToOne
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

    public Double getQuantProdutos() {
        return quantProdutos;
    }

    public void setQuantProdutos(Double quantProdutos) {
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
