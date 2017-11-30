package dao;

import entity.Client;
import entity.Project;
import entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import java.util.List;

@ApplicationScoped
public class ProjectDAO extends SimpleDAO<Project>{

    public List<Project> getAllByClient(Client client){
        return em.createQuery("SELECT p FROM Project p WHERE p.client = :client ORDER BY p.id DESC ").setParameter("client", client).getResultList();
    }

    public List<Project> getAllByUser(User user){
        return em.createQuery("SELECT p FROM Project p WHERE p.client.user = :user").setParameter("user", user).getResultList();
    }

    public Boolean clientHasProject(Client client){
        return this.getAllByClient(client).size() > 0;
    }

}
