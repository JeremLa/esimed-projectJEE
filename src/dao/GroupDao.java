package dao;

import entity.Group;

import javax.faces.bean.ApplicationScoped;
import javax.transaction.Transactional;
import java.io.Serializable;

@ApplicationScoped
public class GroupDao extends SimpleDAO<Group> implements Serializable {

    public Group findOneByName(String name){
        return (Group) em.createQuery("SELECT g FROM Group g WHERE g.groupName = :name").setParameter("name", name).getSingleResult();
    }

    @Transactional
    public void deleteUserGroupByUsername(String username){
        em.createNativeQuery("DELETE FROM security_user_group WHERE username = ?1 ").setParameter(1, username).executeUpdate();
    }
}
