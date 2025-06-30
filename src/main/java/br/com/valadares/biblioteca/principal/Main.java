package br.com.valadares.biblioteca.principal;

import br.com.valadares.biblioteca.dao.EmprestimoDAO;
import br.com.valadares.biblioteca.dao.LivroDAO;
import br.com.valadares.biblioteca.dao.UsuarioDAO;
import br.com.valadares.biblioteca.modelos.Emprestimo;
import br.com.valadares.biblioteca.modelos.Livro;
import br.com.valadares.biblioteca.modelos.Usuario;
import br.com.valadares.biblioteca.util.JPAUtil;

import jakarta.persistence.EntityManager;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        Livro livro = new Livro("A chave da virada", "Marcelo nier",
                "123123123", 10);
        Usuario usuario = new Usuario("Gabriel", "email@gmail.com", "996965910");
        Emprestimo emprestimo = new Emprestimo(LocalDate.now());

        LivroDAO livroDAO = new LivroDAO(em);
        UsuarioDAO usuarioDAO = new UsuarioDAO(em);
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO(em);

        em.getTransaction().begin();

        livroDAO.cadastrar(livro);
        livroDAO.buscarTodos();

        usuarioDAO.cadastrar(usuario);
        usuarioDAO.buscarUsuarios();

        emprestimoDAO.cadastrar(emprestimo);
        emprestimoDAO.buscarTodos();

        em.getTransaction().commit();

        em.close();
    }

}