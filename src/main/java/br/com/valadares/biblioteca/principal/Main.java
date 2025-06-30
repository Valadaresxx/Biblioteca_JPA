package br.com.valadares.biblioteca.principal;

import br.com.valadares.biblioteca.dao.EmprestimoDAO;
import br.com.valadares.biblioteca.dao.LivroDAO;
import br.com.valadares.biblioteca.dao.UsuarioDAO;
import br.com.valadares.biblioteca.modelos.Emprestimo;
import br.com.valadares.biblioteca.modelos.Livro;
import br.com.valadares.biblioteca.modelos.Usuario;
import br.com.valadares.biblioteca.util.JPAUtil;

import jakarta.persistence.EntityManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        Livro livro1 = new Livro("A chave da virada", "Marcelo nier",
                "123123123", 10);
        Livro livro2 = new Livro("Holy bible", "Diversos",
                "784245473", 15);
        Livro livro3 = new Livro("Dragon ball Super - 10 ", "Akira toriyama",
                "954874513", 3);

        Usuario usuario1 = new Usuario("Gabriel", "email@gmail.com", "996965910");
        Usuario usuario2 = new Usuario("cleber", "clebinho@gmail.com", "954876215");
        Usuario usuario3 = new Usuario("Anitta", "anitta@gmail.com", "970706570");

        LivroDAO livroDAO = new LivroDAO(em);
        UsuarioDAO usuarioDAO = new UsuarioDAO(em);
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO(em);

        em.getTransaction().begin();
        livroDAO.cadastrar(livro1);
        livroDAO.cadastrar(livro2);
        livroDAO.cadastrar(livro3);
        usuarioDAO.cadastrar(usuario1);
        usuarioDAO.cadastrar(usuario2);
        usuarioDAO.cadastrar(usuario3);

        emprestimoDAO.cadastrar(livro1, usuario1);
//        emprestimoDAO.cadastrar(livro3, usuario2);
        emprestimoDAO.cadastrar(livro2, usuario3);

        List<Emprestimo> todosEmprestimos = emprestimoDAO.buscarTodos();
        List<Emprestimo> porUsuario = emprestimoDAO.verEmprestimoPorUsuario(2l);

        em.getTransaction().commit();
        emprestimoDAO.deletarId(2l);
        for (Emprestimo emprestimo : todosEmprestimos){
            System.out.println("Todos os emprestimos: " + emprestimo);
        }
        for (Emprestimo emprestimo : porUsuario){
            System.out.println("Emprestimo por usuario: " + emprestimo);
        }
        List<Livro> livroTest = livroDAO.buscarPorNome("Holy bible");
        livroTest.forEach(p -> System.out.println(p.getAutor()));
        Livro livroId = livroDAO.buscarPorId(2l);
        System.out.println(livroId);
        List<Livro> todos = livroDAO.buscarTodos();
        todos.remove(1);
        System.out.println(todos);
        List<Livro> nier = livroDAO.buscarPorAutor("nier");
        System.out.println(nier);
        List<Livro> livroSemEmprestimos = livroDAO.livroSemEmprestimos();
        livroSemEmprestimos.forEach(livro -> System.out.println(livro.getTitulo()));


        em.close();
    }
}