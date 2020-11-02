package br.edu.iff.projetoFarmaceutico;

import br.edu.iff.projetoFarmaceutico.model.Cliente;
import br.edu.iff.projetoFarmaceutico.model.Pedido;
import br.edu.iff.projetoFarmaceutico.model.Produto;
import br.edu.iff.projetoFarmaceutico.model.Representante;
import br.edu.iff.projetoFarmaceutico.repository.ClienteRepository;
import br.edu.iff.projetoFarmaceutico.repository.PedidoRepository;
import br.edu.iff.projetoFarmaceutico.repository.ProdutoRepository;
import br.edu.iff.projetoFarmaceutico.repository.RepresentanteRepository;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoFarmaceuticoApplication implements CommandLineRunner{

    @Autowired
    private RepresentanteRepository representanteRepo;
    @Autowired
    private ProdutoRepository produtoRepo;
    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private PedidoRepository pedidoRepo;
    
    public static void main(String[] args) {
        SpringApplication.run(ProjetoFarmaceuticoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
                
        //Representante
        Representante r1 = new Representante();
        r1.setNome("Rafael");
        r1.setEmail("rafael@hotmail.com");
        r1.setSenha("123rafaEl*");
                
        representanteRepo.save(r1);
        
        //Cliente
        Cliente c1 = new Cliente();
        c1.setCnpj("49.427.566/0001-93");
        c1.setNome("Farmácia do povo");
        
        clienteRepo.save(c1);
        
        //Produto
        Produto prod1 = new Produto();
        prod1.setNome("Esparadrapo");
        prod1.setPreco(15.60);
        prod1.setEmpresa("Eurofarma");
        
        produtoRepo.save(prod1);
        
        //Pedido
        Pedido p1 = new Pedido();
        p1.setQuantProdutos(4);
        Calendar data = new GregorianCalendar();
        data.set(2020, 4, 23, 16, 35, 00);
        p1.setDataPedido(data);
        p1.setCliente(c1);
        p1.setProduto(prod1);
        p1.setRepresentante(r1);
                
        Pedido p2 = new Pedido();
        p2.setQuantProdutos(2);
        Calendar data2 = new GregorianCalendar();
        data2.set(2020, 4, 25, 13, 20, 00);
        p2.setDataPedido(data2);
        p2.setCliente(c1);
        p2.setProduto(prod1);
        p2.setRepresentante(r1);
        
        pedidoRepo.save(p1);
        pedidoRepo.save(p2);
              
                
    }

}
