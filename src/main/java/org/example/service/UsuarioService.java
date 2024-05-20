package org.example.service;

import org.example.entities.Usuario;
import org.example.repositories.UsuarioRepository;

import java.util.List;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario create(Usuario usuario) {
        if (usuario == null || usuario.getNome() == null) {
            throw new IllegalArgumentException("Usuario e nome são obrigatórios");
        }
        return usuarioRepository.Create(usuario).getCliente();
    }

    public List<Usuario> readAll() {
        return usuarioRepository.ReadAll();
    }

    public Usuario readById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return usuarioRepository.ReadById(id);
    }

    public boolean updateById(Usuario usuario, int id) {
        if (usuario == null || usuario.getNome() == null || id <= 0) {
            throw new IllegalArgumentException("Usuario, nome e ID são obrigatórios");
        }
        return usuarioRepository.UpdateById(usuario, id);
    }

    public boolean deleteById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return usuarioRepository.DeleteById(id);
    }
}