package cuk.api.User;

import cuk.api.User.Entities.User;
import cuk.api.User.Request.SignUpRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final SessionFactory sessionFactory;
    @Autowired
    public UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) throws Exception {
        Session session = sessionFactory.openSession();
        User user = new User(signUpRequest);
        session.persist(user);
        System.out.println(user);
        session.close();
    }
}
