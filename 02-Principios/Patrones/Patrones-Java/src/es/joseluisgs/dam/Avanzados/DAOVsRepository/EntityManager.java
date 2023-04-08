package es.joseluisgs.dam.Avanzados.DAOVsRepository;

// Controla la base de datos
public class EntityManager {
    public void persist(User user) {
        System.out.println("Persistiendo usuario");
    }

    public User find(Class<User> userClass, Long id) {
        System.out.println("Buscando usuario");
        return new User();
    }
}
