package dao;

import entity.Client;
import entity.Project;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProjectDao extends SimpleDAO<Project>{

    @Transactional
    public List<Project> getAllByClient(Client client){
        try{
            return em.createQuery("SELECT p FROM Project p WHERE p.client.id = :client").setParameter("client", client.getId()).getResultList();
        }catch(NoResultException e){
            return null;
        }
    }

    @Transactional
    public Boolean clientHasProject(Client client){
        return this.getAllByClient(client) != null;
    }

}
