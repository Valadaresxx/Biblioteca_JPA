package br.com.valadares.biblioteca.principal;

import br.com.valadares.biblioteca.dao.LivroDAO;
import br.com.valadares.biblioteca.modelos.Livro;
import br.com.valadares.biblioteca.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        Livro livro = new Livro("A chave da virada", "Marcelo nier",
                "123123123", 10);
        LivroDAO livroDAO = new LivroDAO(em);

        em.getTransaction().begin();
        livroDAO.cadastrar(livro);
        livroDAO.buscarTodos();
        em.getTransaction().commit();
        em.close();
    }

}