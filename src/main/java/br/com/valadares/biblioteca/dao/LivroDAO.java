package br.com.valadares.biblioteca.dao;

import br.com.valadares.biblioteca.modelos.Livro;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LivroDAO {
    private EntityManager em;

    public LivroDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Livro livro) {
        em.persist(livro);
    }
    public Livro buscarPorId(Long id) {
        return em.find(Livro.class, id);
    }
    public List<Livro> buscarTodos() {
        String jpql = "SELECT l FROM Livro l";
    return em.createQuery(jpql, Livro.class).getResultList();
    }
    public void remove(Livro livro) {
        this.em.remove(this.em.merge(livro));
    }

}
