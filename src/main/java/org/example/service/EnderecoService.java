package org.example.service;

import org.example.entities.Endereco;
import org.example.repositories.EnderecoRepository;

import java.util.List;

public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco create(Endereco endereco) {
        if (endereco == null || endereco.getNumero() == null || endereco.getCep() == null) {
            throw new IllegalArgumentException("Endereço, número e CEP são obrigatórios");
        }
        return enderecoRepository.Create(endereco);
    }

    public List<Endereco> readAll() {
        return enderecoRepository.ReadAll();
    }

    public Endereco readById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return enderecoRepository.ReadById(id);
    }

    public boolean updateById(Endereco endereco, int id) {
        if (endereco == null || endereco.getNumero() == null || endereco.getCep() == null || id <= 0) {
            throw new IllegalArgumentException("Endereço, número, CEP e ID são obrigatórios");
        }
        return enderecoRepository.UpdateById(endereco, id);
    }

    public boolean deleteById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return enderecoRepository.DeleteById(id);
    }
}