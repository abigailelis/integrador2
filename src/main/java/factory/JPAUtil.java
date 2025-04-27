package factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf;

    /**
     * Inicializa la fábrica de entidades utilizando la configuración "integrador2".
     */
    static {
        emf = Persistence.createEntityManagerFactory("integrador2");
    }

    /**
     * Obtiene una nueva instancia de EntityManager
     * @return una instancia EntityManager
     */

    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}


