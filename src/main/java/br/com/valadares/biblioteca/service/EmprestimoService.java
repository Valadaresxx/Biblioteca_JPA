package br.com.valadares.biblioteca.service;

import br.com.valadares.biblioteca.dao.EmprestimoDAO;
import br.com.valadares.biblioteca.exceptions.DAOexceptions;
import br.com.valadares.biblioteca.modelos.Emprestimo;
import br.com.valadares.biblioteca.modelos.Livro;
import br.com.valadares.biblioteca.modelos.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EmprestimoService {

    private EmprestimoDAO emprestimoDAO;

    public EmprestimoService(EntityManager em) {
        this.emprestimoDAO = new EmprestimoDAO(em);
    }

    public void cadastrarEmprestimo(Livro livro, Usuario usuario) {
        if(livro.getEstoque() < 0 ) {
            throw new DAOexceptions("Livro indisponível para empréstimo. Estoque zerado!");
        }
        emprestimoDAO.cadastrar(livro, usuario);
    }
    public Emprestimo buscarPorId(Long id) {
        Emprestimo emprestimo = emprestimoDAO.buscarPorId(id);
        if (emprestimo == null) {
            throw new DAOexceptions("Erro na busca do emprestimo pelo id " + id);
        }
        return emprestimoDAO.buscarPorId(id);
    }
    public List<Emprestimo> buscarPorIdLivro(Long idLivro) {
        List<Emprestimo> emprestimo = emprestimoDAO.buscarEmprestimoPorIdLivro(idLivro);
        if (emprestimo == null) {
            throw new DAOexceptions("Erro na busca do emprestimo pelo id " + idLivro);
        }
        return emprestimoDAO.buscarEmprestimoPorIdLivro(idLivro);
    }
    public void deletarPorId(Long id) {
        Emprestimo emprestimo = emprestimoDAO.buscarPorId(id);
        if (emprestimo == null){
            throw new DAOexceptions("Emprestimo não encontrado.");
        }
        emprestimoDAO.deletarId(id);
    }
}
