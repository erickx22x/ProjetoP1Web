package br.edu.iff.projetoFarmaceutico;

import br.edu.iff.projetoFarmaceutico.model.Cliente;
import br.edu.iff.projetoFarmaceutico.model.Pedido;
import br.edu.iff.projetoFarmaceutico.model.Permissao;
import br.edu.iff.projetoFarmaceutico.model.Produto;
import br.edu.iff.projetoFarmaceutico.model.Representante;
import br.edu.iff.projetoFarmaceutico.repository.ClienteRepository;
import br.edu.iff.projetoFarmaceutico.repository.PedidoRepository;
import br.edu.iff.projetoFarmaceutico.repository.PermissaoRepository;
import br.edu.iff.projetoFarmaceutico.repository.ProdutoRepository;
import br.edu.iff.projetoFarmaceutico.repository.RepresentanteRepository;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    @Autowired
    private PermissaoRepository permissaoRepo;
    
    public static void main(String[] args) {
        SpringApplication.run(ProjetoFarmaceuticoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Permissão

        Permissao p1 = new Permissao();
        p1.setNome("ADMIN");
        Permissao p2 = new Permissao();
        p2.setNome("FUNC");
        permissaoRepo.saveAll(List.of(p1,p2));
        
        
        //Representante
        Representante r1 = new Representante();
        r1.setPermissoes(List.of(p1));
        r1.setNome("Rafael");
        r1.setEmail("rafael@hotmail.com");
        r1.setSenha(new BCryptPasswordEncoder().encode("123rafaEl*"));
                
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
        Pedido pe1 = new Pedido();
        pe1.setQuantProdutos(4);
        Calendar data = new GregorianCalendar();
        data.set(2020, 4, 23, 16, 35, 00);
        pe1.setDataPedido(data);
        pe1.setCliente(c1);
        pe1.setProduto(prod1);
        pe1.setRepresentante(r1);
                
        Pedido pe2 = new Pedido();
        pe2.setQuantProdutos(2);
        Calendar data2 = new GregorianCalendar();
        data2.set(2020, 4, 25, 13, 20, 00);
        pe2.setDataPedido(data2);
        pe2.setCliente(c1);
        pe2.setProduto(prod1);
        pe2.setRepresentante(r1);
        
        pedidoRepo.save(pe1);
        pedidoRepo.save(pe2);
              
                
    }

}
