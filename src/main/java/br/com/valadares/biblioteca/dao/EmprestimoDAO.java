package br.com.valadares.biblioteca.dao;

import br.com.valadares.biblioteca.modelos.Emprestimo;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EmprestimoDAO {
    private EntityManager em;

    public EmprestimoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Emprestimo emprestimo) {
        em.persist(emprestimo);
    }
    public List<Emprestimo> buscarTodos() {
        String jpql = "SELECT p FROM Emprestimo p";
        return em.createQuery(jpql, Emprestimo.class).getResultList();
    }
    public Emprestimo buscarPorId(Long id) {
        return em.find(Emprestimo.class, id);
    }
    public void deletar(Emprestimo emprestimo) {
        this.em.remove(this.em.merge(emprestimo));
    }
}
