package br.com.valadares.biblioteca.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("biblioteca-pu");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }


}
