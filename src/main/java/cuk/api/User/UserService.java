package cuk.api.User;

import cuk.api.User.Entities.User;
import cuk.api.User.Request.SignInRequest;
import cuk.api.User.Request.SignUpRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
@Transactional(readOnly = true)
public class UserService {
    private final SessionFactory sessionFactory;
    @Autowired
    public UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        User user = new User(signUpRequest);
        session.persist(user);
        System.out.println(user);
    }

    public User signIn(SignInRequest signInRequest) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createNamedQuery("User_signIn", User.class)
                        .setParameter("u_id", signInRequest.getU_id())
                                .setParameter("password", signInRequest.getPassword());
        User result = query.getSingleResult();
        return result;
    }
}
