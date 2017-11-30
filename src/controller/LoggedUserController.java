package controller;

import dao.GroupDAO;
import dao.LoginUserDAO;
import dao.UserDAO;
import entity.LoginUser;
import entity.User;
import tools.FacesTools;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@SessionScoped
@Named
public class LoggedUserController implements Serializable{
    @Inject
    private UserDAO userDAO;
    @Inject
    private LoginUserDAO loginUserDAO;
    @Inject
    private GroupDAO groupDAO;
    private User user;
    private String password1;
    private String password2;
    private Boolean mailChanged;

    @PostConstruct
    public void init(){
        mailChanged = false;
    }

    public void saveConnexionInfo(){
        if(!FacesTools.currentUserName().equals(user.getLoginUser().getUserName())){
            if(userDAO.existEmail(user.getLoginUser().getUserName())){
                FacesTools.addMessage(FacesMessage.SEVERITY_ERROR, "Ce mail est déjà utilisé.");
                return;
            }
            user.getLoginUser().setUserName(user.getLoginUser().getUserName());
            mailChanged = true;
        }

        if((password1 != null && password2 != null) && (password1.length() > 0 && password2.length() > 0)) {
            if (password1.equals(password2)) {
                user.getLoginUser().setPassword(FacesTools.digestSHA256Hex(password1));
            } else {
                FacesTools.addMessage(FacesMessage.SEVERITY_ERROR, "Les mots de passe doivent être identique.");
                return;
            }
        }

        saveUserConnectedChange();
    }

    public void saveBusinessInfo(){
        if(user.getCAMax() == null || user.getTaxes() == null){
            FacesTools.addMessage(FacesMessage.SEVERITY_ERROR, "Ces champs n'ont pas été renseigné correctement.");
            return;
        }

        if(user.getCAMax() == 0 || user.getTaxes() == 0){
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Pour que votre tableau de bord soit précis, ces valeurs ne devraient pas être égal à zéro.");
        }

        saveUserConnectedChange();
    }

    public void savePersonnalInfo(){
        saveUserConnectedChange();
    }

    private void saveUserConnectedChange(){
        if(mailChanged){
            LoginUser current = user.getLoginUser();

            LoginUser newLoginUser = new LoginUser();
            newLoginUser.setGroups(current.getGroups());
            newLoginUser.setUserName(current.getUserName());
            newLoginUser.setPassword(current.getPassword());
            groupDAO.deleteUserGroupByUsername(current.getUserName());

            loginUserDAO.insert(newLoginUser);
            user.setLoginUser(newLoginUser);
            userDAO.update(user);

            loginUserDAO.delete(current);

            HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            httpSession.invalidate();
            FacesTools.redirect("/index.xhtml");
        }

        loginUserDAO.update(user.getLoginUser());
        userDAO.update(user);

        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Vos informations ont été sauvegardé avec succès.");
    }

    @Produces
    public User getUser() {
        if(user == null && FacesTools.hasUserPrincipal()){
            user = userDAO.findByUserName(FacesTools.currentUserName());
        }

        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Boolean getMailChanged() {
        return mailChanged;
    }

    public void setMailChanged(Boolean mailChanged) {
        this.mailChanged = mailChanged;
    }
}
