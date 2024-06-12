package cuk.api.Bank;

import cuk.api.Bank.Entities.Bank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@EnableTransactionManagement
@Transactional(readOnly = true)
public class BankService {
    private final SessionFactory sessionFactory;

    @Autowired
    public BankService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public List<Bank> getBankList() throws Exception {
        Session session = sessionFactory.getCurrentSession();

        Query<Bank> query = session.createNamedQuery("Bank_getBankList", Bank.class);
        List<Bank> result = query.getResultList();

        return result;
    }
}
