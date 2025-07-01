package br.com.valadares.biblioteca.dao;

import br.com.valadares.biblioteca.exceptions.DAOexceptions;
import br.com.valadares.biblioteca.modelos.Livro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class LivroDAO {
    private EntityManager em;

    public LivroDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Livro livro) {
        try{
            em.persist(livro);
        }catch (PersistenceException e){
            throw new DAOexceptions("Erro no cadastro do livro.", e);
        }
    }


    public Livro buscarPorId(Long id) {
        try{
            return em.find(Livro.class, id);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca por ID do livro." + id, e);
        }
    }
    public List<Livro> buscarTodos() {
        try{
            String jpql = "SELECT l FROM Livro l";
            return em.createQuery(jpql, Livro.class).getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca dos livros.", e);
        }
    }
    public List<Livro> buscarPorNome(String titulo) {
        try{
            String jpql = "SELECT p FROM Livro p WHERE p.titulo LIKE :titulo";
            return em.createQuery(jpql, Livro.class)
                    .setParameter("titulo", "%" + titulo + "%")
                    .getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca do titulo: " + titulo, e);
        }
    }
    public List<Livro> buscarPorAutor(String autor) {
        try{
            String jpql = "SELECT p FROM Livro p WHERE p.autor LIKE :autor";
            return em.createQuery(jpql, Livro.class)
                    .setParameter("autor", "%" + autor + "%")
                    .getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca do Autor: " + autor, e);
        }
    }
    public List<Livro> buscarLivroSemEmprestimos() {
        try{
            String jpql = "SELECT p FROM Livro p WHERE p not in (SELECT e.livro FROM Emprestimo e)";
            return em.createQuery(jpql, Livro.class).getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca do livros sem emprestimos.", e);
        }
    }
    public List<Livro> buscarTodosComEstoque() {
        try{
            String jpql = "SELECT l FROM Livro l WHERE l.quantidade > 0";
            return em.createQuery(jpql, Livro.class).getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca de livro com emprestimos.",e);
        }
    }


    public void atualizarTitulo(Livro livro, String novoTitulo) {
        try{
            livro = em.merge(livro);
            livro.setTitulo(novoTitulo);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na atualiação do titulo: " + livro.getTitulo(), e);
        }
    }
    public void atualizarAutor(Livro livro, String novoAutor) {
        try {
            livro = em.merge(livro);
            livro.setAutor(novoAutor);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na atualização do Autor: " + livro.getAutor(), e);
        }
    }
    public void atualizarEstoque(Livro livro, int novoEstoque) {
        try{
            livro = em.merge(livro);
            livro.setEstoque(novoEstoque);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na atualização do estoque do livro " + livro.getTitulo(), e);
        }
    }
    public void atualizarInformacoesCompletas(Livro livro, String titulo,
                                              String autor, String isbn, int estoque) {
        try{
            livro = em.merge(livro);
            livro.setTitulo(titulo);
            livro.setAutor(autor);
            livro.setIsbn(isbn);
            livro.setEstoque(estoque);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na atualização do livro: " + livro.toString(), e);
        }
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
