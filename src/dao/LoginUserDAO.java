package dao;

import entity.LoginUser;

import javax.faces.bean.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
public class LoginUserDAO extends SimpleDAO<LoginUser> implements Serializable {

}
