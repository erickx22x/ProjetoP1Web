package br.edu.iff.projetoFarmaceutico.service;

import br.edu.iff.projetoFarmaceutico.exception.NotFoundException;
import br.edu.iff.projetoFarmaceutico.model.Cliente;
import br.edu.iff.projetoFarmaceutico.repository.ClienteRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> findALL(int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }

    public List<Cliente> findALL() {
        return repo.findAll();
    }

    public Cliente findById(Long id) {
        Optional<Cliente> result = repo.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Cliente não encontrado.");
        }
        return result.get();
    }

    public Cliente save(Cliente c) {
        verificaCnpjNomeCadastrado(c.getCnpj(), c.getNome());
        try {
            return repo.save(c);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar cliente.");
        }
    }

    private void verificaCnpjNomeCadastrado(String cnpj, String nome) {
        List<Cliente> result = repo.findByCnpjOrName(cnpj, nome);
        if (!result.isEmpty()) {
            throw new RuntimeException("Cnpj ou Nome já cadastrado.");
        }
    }

    public Cliente update(Cliente c) {
        Cliente obj = findById(c.getIdCliente());
     
        try {
            c.setCnpj(obj.getCnpj());
            return repo.save(c);
        } catch (Exception e) {
            Throwable t = e;
            while (t.getCause()!=null){
                t = t.getCause();
                if(t instanceof ConstraintViolationException){
                    throw((ConstraintViolationException)t);
                }
            }
            throw new RuntimeException("Falha ao atualizar cliente.");
        }
    }

    public void delete(Long id) {
        Cliente obj = findById(id);
        verificaExclusaoClienteComPedido(obj);

        try {
            repo.delete(obj);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao deletar o Cliente");
        }
    }

    private void verificaExclusaoClienteComPedido(Cliente c) {

        if (!c.getPedidos().isEmpty()) {
            throw new RuntimeException("Não é possível excluir cliente pois ele ainda possui pedidos.");
        }
    }
}
