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
    public List<Livro> buscarPorNome(String titulo) {
        String jpql = "SELECT p FROM Livro p WHERE p.titulo LIKE :titulo";
        return em.createQuery(jpql, Livro.class)
                .setParameter("titulo", "%" + titulo + "%")
                .getResultList();
    }
    public List<Livro> buscarPorAutor(String autor) {
        String jpql = "SELECT p FROM Livro p WHERE p.autor LIKE :autor";
        return em.createQuery(jpql, Livro.class)
                .setParameter("autor", "%" + autor + "%")
                .getResultList();
    }
    public List<Livro> buscarLivroSemEmprestimos() {
        String jpql = "SELECT p FROM Livro p WHERE p not in (SELECT e.livro FROM Emprestimo e)";
        return em.createQuery(jpql, Livro.class).getResultList();
    }
    public List<Livro> buscarTodosComEstoque() {
        String jpql = "SELECT l FROM Livro l WHERE l.quantidade > 0";
        return em.createQuery(jpql, Livro.class).getResultList();
    }


    public void atualizarTitulo(Livro livro, String novoTitulo) {
        livro = em.merge(livro);
        livro.setTitulo(novoTitulo);
    }
    public void atualizarAutor(Livro livro, String novoAutor) {
        livro = em.merge(livro);
        livro.setAutor(novoAutor);
    }
    public void atualizarEstoque(Livro livro, int novoEstoque) {
        livro = em.merge(livro);
        livro.setEstoque(novoEstoque);
    }
    public void atualizarInformacoesCompletas(Livro livro, String titulo,
                                              String autor, String isbn, int estoque) {
        livro = em.merge(livro);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setIsbn(isbn);
        livro.setEstoque(estoque);
    }


    public void remove(Livro livro) {
        this.em.remove(this.em.merge(livro));
    }
    public void removePorId(Long id) {
        Livro livro = em.find(Livro.class, id);
        if(livro != null){
            this.em.remove(livro);
        }
    }
}
