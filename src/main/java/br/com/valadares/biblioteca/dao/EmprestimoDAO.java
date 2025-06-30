package br.com.valadares.biblioteca.dao;

import br.com.valadares.biblioteca.modelos.Emprestimo;
import br.com.valadares.biblioteca.modelos.Livro;
import br.com.valadares.biblioteca.modelos.Usuario;
import jakarta.persistence.EntityManager;

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
    public void deletar(Emprestimo emprestimo) {
        Livro livro = emprestimo.getLivro();
        livro.setEstoque(livro.getEstoque() + 1);
        em.merge(livro);

        em.remove(em.merge(emprestimo));
    }
    public List<Emprestimo> verEmprestimoPorUsuario(Long idUsuario) {
        String jpql = "SELECT p FROM Emprestimo p WHERE p.usuario.id = :idUsuario";
        return em.createQuery(jpql, Emprestimo.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();
    }
}
