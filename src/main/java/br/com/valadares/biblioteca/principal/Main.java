package br.com.valadares.biblioteca.principal;

import br.com.valadares.biblioteca.dao.LivroDAO;
import br.com.valadares.biblioteca.dao.UsuarioDAO;
import br.com.valadares.biblioteca.modelos.Livro;
import br.com.valadares.biblioteca.modelos.Usuario;
import br.com.valadares.biblioteca.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        Livro livro = new Livro("A chave da virada", "Marcelo nier",
                "123123123", 10);
        Usuario usuario = new Usuario("Gabriel", "email@gmail.com", "996965910");

        LivroDAO livroDAO = new LivroDAO(em);
        UsuarioDAO usuarioDAO = new UsuarioDAO(em);

        em.getTransaction().begin();

        livroDAO.cadastrar(livro);
        livroDAO.buscarTodos();

        usuarioDAO.cadastrar(usuario);
        usuarioDAO.buscarUsuarios();

        em.getTransaction().commit();

        em.close();
    }

}