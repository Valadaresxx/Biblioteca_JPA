package br.com.valadares.biblioteca.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "emprestimos")
public class Emprestimos {
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
    public Emprestimos() {
    }

    public Emprestimos(LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista, LocalDate dataDevolucaoReal) {
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoReal = dataDevolucaoReal;
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
