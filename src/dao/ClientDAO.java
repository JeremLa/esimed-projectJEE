package dao;

import entity.Client;
import entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClientDAO extends SimpleDAO<Client> {
    @Transactional
    public List<Client> searchClient(String search){
        return em.createQuery("SELECT c FROM Client c WHERE LOWER(c.lastName) LIKE :search " +
                                                           "OR LOWER(c.firstName) LIKE :search " +
                                                           "OR LOWER(c.contactName) LIKE :search " +
                                                           "OR LOWER(c.city) LIKE :search " +
                                                           "OR LOWER(c.mail) LIKE :search " +
                                                           "OR LOWER(c.phone) LIKE :search")
                .setParameter("search", "%"+search.toLowerCase()+"%").getResultList();
    }

    @Transactional
    public List<Client> getAllByUser(User user){
        return em.createQuery("SELECT c FROM Client c WHERE c.user.id = :user").setParameter("user", user.getId()).getResultList();
    }
}
