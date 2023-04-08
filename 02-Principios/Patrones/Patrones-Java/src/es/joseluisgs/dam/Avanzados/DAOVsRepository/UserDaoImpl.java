package es.joseluisgs.dam.Avanzados.DAOVsRepository;

public class UserDaoImpl implements UserDao {

    // Aqui debemos llamar a la base de datos
    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User read(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(String userName) {

    }

}