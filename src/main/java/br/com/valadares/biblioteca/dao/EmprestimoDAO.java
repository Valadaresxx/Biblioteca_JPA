package br.com.valadares.biblioteca.dao;

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
        if (livro.getEstoque() <= 0){
            throw new RuntimeException("Livro indisponível para empréstimo. Estoque zerado!");
        }
        livro.setEstoque(livro.getEstoque() - 1);
        em.merge(livro);
        Emprestimo emprestimo = new Emprestimo(livro, usuario);
        em.persist(emprestimo);
    }


    public List<Emprestimo> buscarTodos() {
        String jpql = "SELECT p FROM Emprestimo p";
        return em.createQuery(jpql, Emprestimo.class).getResultList();
    }
    public List<Emprestimo> buscarEmprestimoPorIdUsuario(Long idUsuario) {
        String jpql = "SELECT p FROM Emprestimo p WHERE p.usuario.id = :id";
        return em.createQuery(jpql, Emprestimo.class)
                .setParameter("id", idUsuario)
                .getResultList();
    }
    public List<Emprestimo> buscarEmprestimoPorIdLivro(Long idLivro) {
        String jpql = "SELECT p FROM Emprestimo p WHERE p.livro.id = :id";
        return em.createQuery(jpql, Emprestimo.class)
                .setParameter("id", idLivro)
                .getResultList();
    }
    public Emprestimo buscarPorId(Long id) {
        return em.find(Emprestimo.class, id);
    }
    public List<Emprestimo> buscarTodosNaoDevolvidos() {
        String jpql = "SELECT p FROM Emprestimo p WHERE p.dataDevolucaoReal IS NULL ";
        return em.createQuery(jpql, Emprestimo.class).getResultList();
    }
    public List<Emprestimo> buscarAtrasados() {
        String jpql = "SELECT p FROM Emprestimos p WHERE p.dataDevolucaoPrevista IS < :hoje";
        return em.createQuery(jpql, Emprestimo.class)
                .setParameter("hoje", LocalDate.now())
                .getResultList();
    }


    public void registraDevolucao(Emprestimo emprestimo) {
        emprestimo.setDataDevolucaoReal(LocalDate.now());
        Livro livro = emprestimo.getLivro();
        livro.setEstoque(livro.getEstoque() + 1);
        em.merge(livro);
        em.merge(emprestimo);
    }


    public void deletar(Emprestimo emprestimo) {
        Livro livro = emprestimo.getLivro();
        livro.setEstoque(livro.getEstoque() + 1);
        em.merge(livro);

        em.remove(em.merge(emprestimo));
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
