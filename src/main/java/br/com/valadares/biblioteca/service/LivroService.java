package br.com.valadares.biblioteca.service;

import br.com.valadares.biblioteca.dao.LivroDAO;
import br.com.valadares.biblioteca.exceptions.DAOexceptions;
import br.com.valadares.biblioteca.modelos.Livro;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LivroService {
    private LivroDAO livroDAO;

    public LivroService(EntityManager em) {
        this.livroDAO = new LivroDAO(em);
    }

    public void cadastrarLivro(Livro livro) {
        if (livro == null){
            throw new DAOexceptions("Livro informado está nulo. Verifique os dados.");
        }
        if (livro.getEstoque() <= 0 ){
            throw new DAOexceptions("Estoque precisa ser maior que 0");
        }
        if (livro.getTitulo().trim().isEmpty()){
            throw new DAOexceptions("Erro no cadastro, O Titulo está vazio!");
        }
        if (livro.getAutor().trim().isEmpty()){
            throw new DAOexceptions("Erro no cadastro, o Autor está vazio!");
        }
        if (livro.getIsbn().trim().isEmpty()){
            throw new DAOexceptions("Erro no cadastro, o Isbn está vazio!");
        }
        livroDAO.cadastrar(livro);
    }
    public Livro buscarPorId(Long id) {
        Livro livro = livroDAO.buscarPorId(id);
        if(livro == null){
            throw new DAOexceptions("Livro com id " + id + "não encontrado!");
        }
        return livro;
    }
    public void removeLivro(Livro livro) {
        List<Livro> livroSemEmprestimos = livroDAO.buscarLivroSemEmprestimos();
        boolean podeRemover = livroSemEmprestimos.stream()
                .anyMatch(l -> l.getId().equals(livro.getId()));

        if (podeRemover) {
            livroDAO.remove(livro);
        } else {
            throw new DAOexceptions("Livro não pode ser removido. Ele está associado a um empréstimo.");
        }
    }
    public void removeLivroId(Long id) {
        Livro livro = livroDAO.buscarPorId(id);
        if(livro == null){
            throw new DAOexceptions("Livro com id " + id + "não encontrado");
        }
        livroDAO.remove(livro);
    }
}
