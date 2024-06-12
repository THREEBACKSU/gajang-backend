package cuk.api.Address;

import cuk.api.Address.Entities.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@EnableTransactionManagement
@Transactional(readOnly = true)
public class AddressService {
    private final SessionFactory sessionFactory;

    @Autowired
    public AddressService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<String> getProvince() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Query<String> query = session.createNamedQuery("Address_getProvince", String.class);
        List<String> result = query.getResultList();
        return result;
    }

    public List<String> getCityByProvince(String province) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Query<String> query = session.createNamedQuery("Address_getCityByProvince", String.class)
                .setParameter("province", province);
        List<String> result = query.getResultList();
        return result;
    }

    public List<String> getTownByProvinceAndCity(String province, String city) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Query<String> query = session.createNamedQuery("Address_getTownByProvinceAndCity", String.class)
                .setParameter("province", province)
                .setParameter("city", city);
        List<String> result = query.getResultList();
        return result;
    }

    public int getAddressId(String province, String city, String town) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Query<Integer> query = session.createNamedQuery("Address_getAddressId", Integer.class)
                .setParameter("province", province)
                .setParameter("city", city)
                .setParameter("town", town);
        int address_id = query.stream().findAny().orElse(-1);
        return address_id;
    }
}
