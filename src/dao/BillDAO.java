package dao;

import entity.Bill;
import entity.Client;
import entity.Project;
import entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BillDAO extends SimpleDAO<Bill>{

    public List<Bill> getByUser(User user){
        return em.createQuery("SELECT b from Bill b WHERE b.project.client.user = :user").setParameter("user", user).getResultList();
    }

    public List<Bill> getByProject(Project project){
        return em.createQuery("SELECT b from Bill b WHERE b.project = :project").setParameter("project", project).getResultList();
    }

    public List<Bill> getByClient(Client client){
        return em.createQuery("SELECT b from Bill b WHERE b.project.client = :client").setParameter("client", client).getResultList();
    }

    public Integer getSequentNumber(User user){
        Integer max = (Integer) em.createQuery("SELECT MAX(b.billNumber) FROM Bill b WHERE b.project.client.user = :user").setParameter("user", user).getSingleResult();

        return max == 0 ? 1 : max + 1;
    }

    public Boolean existBillNumber(Integer number){
        return em.createQuery("SELECT b FROM Bill b WHERE b.billNumber = :number").setParameter("number", number).getResultList().size() > 0;
    }
}
