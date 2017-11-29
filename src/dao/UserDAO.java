package dao;

import entity.User;

import javax.faces.bean.ApplicationScoped;
import javax.persistence.NoResultException;
import java.io.Serializable;

@ApplicationScoped
public class UserDAO extends SimpleDAO<User> implements Serializable{

    public User findByUserName(String mail){
        try{
            return (User) em.createQuery("SELECT u from User u WHERE u.loginUser.userName = :mail").setParameter("mail", mail).getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    public Boolean existEmail(String mail){
        return this.findByUserName(mail) != null;
    }

}
