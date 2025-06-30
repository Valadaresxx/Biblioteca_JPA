package br.com.valadares.biblioteca.dao;

import br.com.valadares.biblioteca.modelos.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UsuarioDAO {
    private EntityManager em;

    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Usuario usuario) {
        em.persist(usuario);
    }
    public Usuario buscarUsuarioId(Long id) {
        return this.em.find(Usuario.class, id);
    }
    public List<Usuario> buscarUsuarios() {
        String jpql = "SELECT p FROM Usuario p";
        return em.createQuery(jpql, Usuario.class).getResultList();
    }
    public void deletar(Usuario usuario) {
        em.remove(this.em.merge(usuario));
    }
}
