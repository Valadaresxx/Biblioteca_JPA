package br.com.valadares.biblioteca.dao;

import br.com.valadares.biblioteca.exceptions.DAOexceptions;
import br.com.valadares.biblioteca.modelos.Emprestimo;
import br.com.valadares.biblioteca.modelos.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UsuarioDAO {
    private EntityManager em;

    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Usuario usuario) {
        try{
            em.persist(usuario);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao cadastrar.", e);
        }
    }


    public Usuario buscarUsuarioId(Long id) {
        try{
            return this.em.find(Usuario.class, id);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao buscar usuario pelo id: " + id, e);
        }
    }
    public List<Usuario> buscarTodos() {
        try{
            String jpql = "SELECT p FROM Usuario p";
            return em.createQuery(jpql, Usuario.class).getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao buscar todos os usuarios. ", e);
        }
    }
    public List<Usuario> buscarPorNome(String nome) {
        try{
            String jpql = "SELECT p FROM Usuario p WHERE p.nome LIKE :nome";
            return em.createQuery(jpql, Usuario.class)
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao buscar usuario por nome: " + nome, e);
        }
    }
    public List<Usuario> buscarPorEmail(String email) {
        try{
            String jpql = "SELECT p FROM Usuario p WHERE p.email = :email";
            return em.createQuery(jpql, Usuario.class)
                    .setParameter("email", email)
                    .getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao buscar usuario pelo email: " + email, e);
        }
    }


    public void atualizarNome(Usuario usuario, String novoNome) {
        try{
            usuario = em.merge(usuario);
            usuario.setNome(novoNome);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao atualizar o nome do usuario: " + usuario.getNome(), e);
        }
    }
    public void atualizarEmail(Usuario usuario, String novoEmail) {
        try{
            usuario = em.merge(usuario);
            usuario.setEmail(novoEmail);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao atualizar o email do usuario: " + usuario.getEmail(), e);
        }
    }
    public void atualizarTelefone(Usuario usuario, String novoTelefone) {
        try{
            usuario = em.merge(usuario);
            usuario.setTelefone(novoTelefone);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao atualizar o telefone do usuario: ", e);
        }
    }
    public void atualizarInformacoes(Usuario usuario, String novoNome, String novoEmail, String novoTelefone) {
        try{
            usuario = em.merge(usuario);
            usuario.setNome(novoNome);
            usuario.setEmail(novoEmail);
            usuario.setTelefone(novoTelefone);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao atualizar as informações do usuario: ", e);
        }
    }


    public boolean verificaSePossuiEmprestimo(Long idUsuario) {
        try{
            String jpql = "SELECT COUNT(p) FROM Emprestimo p WHERE p.usuario.id = :id AND p.dataDevolucaoReal IS NULL";
            Long resultado = em.createQuery(jpql, Long.class)
                    .setParameter("id", idUsuario)
                    .getSingleResult();
            return resultado > 0;

//            Usuario usuario = em.find(Usuario.class, idUsuario);
//            if(usuario == null){
//                throw new DAOexceptions("Usuario com id " + idUsuario + "não encontrado.");
//            }
//            return usuario.getEmprestimos()
//                    .stream()
//                    .anyMatch(e -> e.getDataDevolucaoReal() == null);
        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao verificar se o usuario possui emprestimo. ", e);
        }
    }


    public void deletar(Usuario usuario) {
        em.remove(this.em.merge(usuario));
    }
    public void deletarPorId(Long idUsuario) {
        Usuario usuario = em.find(Usuario.class, idUsuario);
        if(usuario != null){
            em.remove(em.merge(usuario));
        }
    }
}
