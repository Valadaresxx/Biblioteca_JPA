package br.com.valadares.biblioteca.dao;

import br.com.valadares.biblioteca.exceptions.DAOexceptions;
import br.com.valadares.biblioteca.modelos.Emprestimo;
import br.com.valadares.biblioteca.modelos.Livro;
import br.com.valadares.biblioteca.modelos.Usuario;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoDAO {
    private EntityManager em;

    public EmprestimoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Livro livro, Usuario usuario) {
        try{
            livro.setEstoque(livro.getEstoque() - 1);
            em.merge(livro);
            Emprestimo emprestimo = new Emprestimo(livro, usuario);
            em.persist(emprestimo);
        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao cadastrar o emprestimo.", e);
        }
    }


    public List<Emprestimo> buscarTodos() {
        try{
            String jpql = "SELECT p FROM Emprestimo p";
            return em.createQuery(jpql, Emprestimo.class).getResultList();
        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca de todos os emprestimos.",e);
        }
    }
    public List<Emprestimo> buscarEmprestimoPorIdUsuario(Long idUsuario) {
        try{
            String jpql = "SELECT p FROM Emprestimo p WHERE p.usuario.id = :id";
            return em.createQuery(jpql, Emprestimo.class)
                    .setParameter("id", idUsuario)
                    .getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca do emprestimo pelo id: " + idUsuario, e);
        }
    }
    public List<Emprestimo> buscarEmprestimoPorIdLivro(Long idLivro) {
        try{
            String jpql = "SELECT p FROM Emprestimo p WHERE p.livro.id = :id";
            return em.createQuery(jpql, Emprestimo.class)
                    .setParameter("id", idLivro)
                    .getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca do emprestimo por id do livro: " + idLivro, e);
        }
    }
    public Emprestimo buscarPorId(Long id) {
        try{
            return em.find(Emprestimo.class, id);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca de emprestimo pelo id: " + id, e);
        }
    }
    public List<Emprestimo> buscarTodosNaoDevolvidos() {
        try{
            String jpql = "SELECT p FROM Emprestimo p WHERE p.dataDevolucaoReal IS NULL ";
            return em.createQuery(jpql, Emprestimo.class).getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca dos emprestimos não finalizados.", e);
        }
    }
    public List<Emprestimo> buscarAtrasados() {
        try{
            String jpql = "SELECT p FROM Emprestimo p WHERE p.dataDevolucaoPrevista < :hoje";
            return em.createQuery(jpql, Emprestimo.class)
                    .setParameter("hoje", LocalDate.now())
                    .getResultList();

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro na busca dos emprestimos atrasados.", e);
        }
    }


    public void registraDevolucao(Emprestimo emprestimo) {
        try{
            emprestimo.setDataDevolucaoReal(LocalDate.now());
            Livro livro = emprestimo.getLivro();
            livro.setEstoque(livro.getEstoque() + 1);
            em.merge(livro);
            em.merge(emprestimo);

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro no registro de devolução do emprestimo. ", e);
        }
    }


    public void deletar(Emprestimo emprestimo) {
        try{
            Livro livro = emprestimo.getLivro();
            livro.setEstoque(livro.getEstoque() + 1);
            em.merge(livro);
            em.remove(em.merge(emprestimo));

        } catch (RuntimeException e) {
            throw new DAOexceptions("Erro ao exclur emprestimo.", e);
        }
    }
    public void deletarId(Long id) {
        Emprestimo emprestimo = em.find(Emprestimo.class, id);

        if (emprestimo != null){
            Livro livro = emprestimo.getLivro();
            livro.setEstoque(livro.getEstoque() + 1);
            em.merge(livro);

            em.remove(emprestimo);
        }else{
            System.out.println("Emprestimo não encontrado.");
        }
    }
}
