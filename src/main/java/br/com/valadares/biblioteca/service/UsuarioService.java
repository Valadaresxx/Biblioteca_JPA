package br.com.valadares.biblioteca.service;

import br.com.valadares.biblioteca.dao.UsuarioDAO;
import br.com.valadares.biblioteca.exceptions.DAOexceptions;
import br.com.valadares.biblioteca.modelos.Usuario;

import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public void cadastrarUsuario(Usuario usuario) {
        String nome = usuario.getNome();
        String email = usuario.getEmail();
        String telefone = usuario.getTelefone();
        if(nome.trim().isEmpty()) {
            throw new DAOexceptions("Erro! Nome do usuario está vazio.");
        }
        if(email.trim().isEmpty()) {
            throw new DAOexceptions("Erro! Nome do email está vazio.");
        }
        if(telefone.trim().isEmpty()) {
            throw new DAOexceptions("Erro! Nome do telefone está vazio.");
        }
        usuarioDAO.cadastrar(usuario);
    }
    public Usuario buscarPorId(Long id) {
        Usuario usuario = usuarioDAO.buscarUsuarioId(id);
        if (usuario == null) {
            throw new DAOexceptions("Erro na busca do usuario por id.");
        }
        return usuarioDAO.buscarUsuarioId(id);
    }
    public List<Usuario> buscarPeloEmail(String email) {
        List<Usuario> usuario = usuarioDAO.buscarPorEmail(email);
        if (email.trim().isEmpty()) {
            throw new DAOexceptions("Erro na busca do email pelo email.");
        }
        return usuarioDAO.buscarPorEmail(email);
    }
    public List<Usuario> buscarPeloNome(String nome) {
        List<Usuario> usuarios = usuarioDAO.buscarPorNome(nome);
        if (nome.trim().isEmpty()) {
            throw new DAOexceptions("Erro na busca do usuario pelo nome.");
        }
        return usuarioDAO.buscarPorNome(nome);
    }
    public boolean verificaSemTemEmprestimo(Long idUsuario) {
        return usuarioDAO.verificaSePossuiEmprestimo(idUsuario);
    }
    public void deletarId(Long id) {
        if(verificaSemTemEmprestimo(id)) {
            throw new DAOexceptions("O usuario possui emprestimo, impossivel excluir.");
        }
        usuarioDAO.deletarPorId(id);
    }

}
