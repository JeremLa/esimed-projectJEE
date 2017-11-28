package dao;

import entity.LoginUser;

import javax.faces.bean.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
public class LoginUserDao extends SimpleDAO<LoginUser> implements Serializable {

}
