package com.teste.sementes.service;
import com.teste.sementes.domain.Endereco;
import com.teste.sementes.repository.EnderecoRepository;
import org.springframework.stereotype.Service;


@Service
public class EnderecoService extends GenericService<Endereco, EnderecoRepository> {

    public EnderecoService(EnderecoRepository repository) {
        super(repository);
    }

}
