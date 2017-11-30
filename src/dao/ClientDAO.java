package dao;

import entity.Client;
import entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ClientDAO extends SimpleDAO<Client> {
    public List<Client> searchClient(User user, String search){
        return em.createQuery("SELECT c FROM Client c WHERE (LOWER(c.lastName) LIKE :search " +
                                                           "OR LOWER(c.firstName) LIKE :search " +
                                                           "OR LOWER(c.contactName) LIKE :search " +
                                                           "OR LOWER(c.city) LIKE :search " +
                                                           "OR LOWER(c.mail) LIKE :search " +
                                                           "OR LOWER(c.phone) LIKE :search) AND c.user = :user")
                .setParameter("search", "%"+search.toLowerCase()+"%")
                .setParameter("user", user)
                .getResultList();
    }

    public List<Client> getAllByUser(User user){
        return em.createQuery("SELECT c FROM Client c WHERE c.user.id = :user").setParameter("user", user.getId()).getResultList();
    }
}
