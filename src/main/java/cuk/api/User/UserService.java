package cuk.api.User;

import cuk.api.User.Entities.User;
import cuk.api.User.Request.SignInRequest;
import cuk.api.User.Request.SignUpRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
@Transactional(readOnly = true)
public class UserService {
    private final SessionFactory sessionFactory;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(SessionFactory sessionFactory, PasswordEncoder passwordEncoder) {
        this.sessionFactory = sessionFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest, int address_id) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        String encoderedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encoderedPassword);

        User user = new User(signUpRequest, address_id);
        session.persist(user);
    }

    public User signIn(SignInRequest signInRequest) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createNamedQuery("User_signIn", User.class)
                        .setParameter("u_id", signInRequest.getU_id())
                                .setParameter("password", signInRequest.getPassword());
        User result = query.getSingleResult();
        return result;
    }

    public User isDuplicatedId(String u_id) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createNamedQuery("User_isDuplicatedID", User.class)
                .setParameter("u_id", u_id);
        User result = query.stream().findAny().orElse(null);
        return result;
    }

    public User isDuplicatedName(String name) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createNamedQuery("User_isDuplicatedName", User.class)
                .setParameter("name", name);
        User result = query.stream().findAny().orElse(null);
        return result;
    }
}
