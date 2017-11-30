package dao;

import entity.Bill;
import entity.Client;
import entity.Project;
import entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BillDAO extends SimpleDAO<Bill>{

    @Transactional
    public List<Bill> getByUser(User user){
        return em.createQuery("SELECT b from Bill b WHERE b.project.client.user = :user").setParameter("user", user).getResultList();
    }

    @Transactional
    public List<Bill> getByProject(Project project){
        return em.createQuery("SELECT b from Bill b WHERE b.project = :project").setParameter("project", project).getResultList();
    }

    @Transactional
    public List<Bill> getByClient(Client client){
        return em.createQuery("SELECT b from Bill b WHERE b.project.client = :client").setParameter("client", client).getResultList();
    }

    @Transactional
    public Integer getSequentNumber(User user){
        Integer max = (Integer) em.createQuery("SELECT MAX(b.billNumber) FROM Bill b WHERE b.project.client.user = :user").setParameter("user", user).getSingleResult();

        return max == 0 ? 1 : max;
    }
}
