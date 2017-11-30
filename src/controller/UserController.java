package controller;

import dao.GroupDAO;
import dao.LoginUserDAO;
import dao.UserDAO;
import entity.Group;
import entity.LoginUser;
import entity.User;
import org.primefaces.context.RequestContext;
import tools.FacesTools;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ViewScoped
@Named
public class UserController implements Serializable{
    @Inject
    private UserDAO userDAO;
    @Inject
    private GroupDAO groupDAO;
    @Inject
    private LoginUserDAO loginUserDAO;
    @Inject
    private LoggedUserController loggedUserController;
    @Inject
    private User user;

    private LoginUser loginUser = new LoginUser();
    private User newUser = new User();
//    private User loggedUser;
    private String password1 = "";
    private String password2 = "";
//    private Boolean mailChanged = false;

    public void insertUser(){
        Group group = groupDAO.findOneByName("regulars");
        loginUser.setPassword(FacesTools.digestSHA256Hex(password1));
        loginUser.addGroup(group);
        loginUserDAO.insert(loginUser);

        newUser.setLoginUser(loginUser);

        userDAO.insert(newUser);

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('inscription').hide();");

        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Le compte a été créé avec succès, vous pouvez vous connecter.");
    }

//    public Boolean mailExist(String mail){
//        return userDAO.existEmail(mail);
//    }

//    public void saveConnexionInfo(){
//        if(!FacesTools.currentUserName().equals(loggedUser.getLoginUser().getUserName())){
//            if(userDAO.existEmail(loggedUser.getLoginUser().getUserName())){
//                FacesTools.addMessage(FacesMessage.SEVERITY_ERROR, "Ce mail est déjà utilisé.");
//                return;
//            }
//            loginUser.setUserName(loggedUser.getLoginUser().getUserName());
//            mailChanged = true;
//        }
//
//        if((password1 != null && password2 != null) && (password1.length() > 0 && password2.length() > 0)) {
//            if (password1.equals(password2)) {
//                if(mailChanged){
//                    loginUser.setPassword(FacesTools.digestSHA256Hex(password1));
//                }else{
//                    loggedUser.getLoginUser().setPassword(FacesTools.digestSHA256Hex(password1));
//                }
//            } else {
//                FacesTools.addMessage(FacesMessage.SEVERITY_ERROR, "Les mots de passe doivent être identique.");
//                return;
//            }
//        }else{
//            if(mailChanged){
//                loginUser.setPassword(loggedUser.getLoginUser().getPassword());
//            }
//        }
//
//        saveUserConnectedChange();
//    }
//
//    public void saveBusinessInfo(){
//        if(loggedUser.getCAMax() == null || loggedUser.getTaxes() == null){
//            FacesTools.addMessage(FacesMessage.SEVERITY_ERROR, "Ces champs n'ont pas été renseigné correctement.");
//            return;
//        }
//
//        if(loggedUser.getCAMax() == 0 || loggedUser.getTaxes() == 0){
//            FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Pour que votre tableau de bord soit précis, ces valeurs ne devraient pas être égal à zéro.");
//        }
//
//        saveUserConnectedChange();
//    }
//
//    public void savePersonnalInfo(){
//        saveUserConnectedChange();
//    }
//
//    private void saveUserConnectedChange(){
//        if(mailChanged){
//            LoginUser current = loginUserDAO.get(LoginUser.class, loggedUser.getLoginUser().getId());
//
//            loginUser.setGroups(current.getGroups());
//            groupDAO.deleteUserGroupByUsername(current.getUserName());
//
//            loginUserDAO.insert(loginUser);
//            loggedUser.setLoginUser(loginUser);
//            userDAO.update(loggedUser);
//
//            loginUserDAO.delete(current);
//
//            HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//            httpSession.invalidate();
//            FacesTools.redirect("/index.xhtml");
//        }
//
//        loginUserDAO.update(loggedUser.getLoginUser());
//        userDAO.update(loggedUser);
//
//        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Vos informations ont été sauvegardé avec succès.");
//    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }
}
