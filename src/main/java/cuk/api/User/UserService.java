package cuk.api.User;

import cuk.api.User.Entities.User;
import cuk.api.User.Request.SignUpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void signUp(User user) {

    }
}
