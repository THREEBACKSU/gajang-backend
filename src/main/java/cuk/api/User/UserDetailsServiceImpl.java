package cuk.api.User;

import cuk.api.User.Entities.SecurityUserDetails;
import cuk.api.User.Entities.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SessionFactory sessionFactory;
    @Override
    public UserDetails loadUserByUsername(String u_id) throws UsernameNotFoundException {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createNamedQuery("User_loadUserByU_id", User.class)
                .setParameter("u_id", u_id);
        User user = query.stream().findAny().orElseThrow(() -> new UsernameNotFoundException("Not found User"));
        return new SecurityUserDetails(user);
    }
}
