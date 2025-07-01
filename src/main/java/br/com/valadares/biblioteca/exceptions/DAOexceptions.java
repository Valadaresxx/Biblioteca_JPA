package br.com.valadares.biblioteca.exceptions;

public class DAOexceptions extends RuntimeException{

    public DAOexceptions(String message) {
        super(message);
    }

    public DAOexceptions(String message, Throwable cause) {
        super(message, cause);
    }

}
