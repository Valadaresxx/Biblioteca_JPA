package br.com.valadares.biblioteca.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "emprestimos")
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    @ManyToOne(fetch = FetchType.LAZY)
    private Livro livro;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
    public Emprestimo() {
    }

    public Emprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
//        this.dataDevolucaoPrevista = this.dataEmprestimo + ;
//        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }
}
