package repository;



import domain.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl extends BaseRepository implements UserRepository{
    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public User save(User user) {
        return this.executeTransaction((em) -> {
            em.persist(user);
            return user;
        });
    }

    @Override
    public User update(User user) {
        return this.executeTransaction((em) -> {
            em.merge(user);
            return user;
        });
    }

    @Override
    public List<User> findAll() {
        return this.executeTransaction((em) -> {
            return em.createNativeQuery("SELECT * FROM users", User.class)
                    .getResultList();
        });
    }

    @Override
    public User findById(String id) {
        return this.executeTransaction((em) -> {
            return (User) em.createNativeQuery("SELECT * FROM users WHERE id = '" + id + "'", User.class)
                    .getSingleResult();
        });
    }

    @Override
    public User findByUsername(String username) {
        return this.executeTransaction((em) -> {
            return (User) em.createNativeQuery("SELECT * FROM users WHERE username = '" + username + "'", User.class)
                    .getSingleResult();
        });
    }

    @Override
    public void deleteFriend(String id, String secondId) {
        this.executeTransaction((em) -> {
            em.createNativeQuery
                    ("DELETE FROM users_friends WHERE user_id='" + id + "'" + "&& friend_id = '" + secondId + "'")
                    .executeUpdate();
            return null;
        });
    }
}
