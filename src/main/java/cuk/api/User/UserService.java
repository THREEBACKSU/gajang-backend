package cuk.api.User;

import cuk.api.User.Entities.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    public void signUp() {
        User user = entityManager.find(User.class, "j2ee");
        System.out.println(user);
    }
}
