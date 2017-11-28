package controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import dao.GroupDao;
import dao.LoginUserDao;
import dao.UserDAO;
import entity.Group;
import entity.LoginUser;
import entity.User;
import org.primefaces.context.RequestContext;
import tools.FacesTools;
import tools.GrowlView;
import tools.UniqueEmailValidator;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@SessionScoped
@Named
public class UserController implements Serializable{
    @Inject
    private UserDAO userDAO;
    @Inject
    private GroupDao groupDao;
    @Inject
    private LoginUserDao loginUserDao;

    private LoginUser loginUser = new LoginUser();
    private User user = new User();
    private User connectedUser;
    private String password1 = "";
    private String password2 = "";
    private Boolean mailChanged = false;

    public void insertUser(){
        Group group = groupDao.findOneByName("regulars");
        loginUser.setPassword(FacesTools.digestSHA256Hex(password1));
        loginUser.addGroup(group);
        loginUserDao.insert(loginUser);

        user.setLoginUser(loginUser);

        userDAO.insert(user);

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('inscription').hide();");

        FacesTools.addFlashMessage(FacesMessage.SEVERITY_INFO, "Le compte a été créé avec succès, vous pouvez vous connecter.");
    }

    public Boolean mailExist(String mail){
        return userDAO.existEmail(mail);
    }

    public String currentUserName(){
        return FacesTools.getRequest().getUserPrincipal().getName();
    }

    public void registerConnectedUser(){
        this.connectedUser = userDAO.findByMail(this.currentUserName());
    }

    public void saveConnexionInfo(){
        if(!currentUserName().equals(connectedUser.getLoginUser().getUserName())){
            if(userDAO.existEmail(connectedUser.getLoginUser().getUserName())){
                FacesTools.addFlashMessage(FacesMessage.SEVERITY_ERROR, "Ce mail est déjà utilisé.");
                return;
            }
            loginUser.setUserName(connectedUser.getLoginUser().getUserName());
            mailChanged = true;
        }

        if((password1 != null && password2 != null) && (password1.length() > 0 && password2.length() > 0)) {
            if (password1.equals(password2)) {
                if(mailChanged){
                    loginUser.setPassword(FacesTools.digestSHA256Hex(password1));
                }else{
                    connectedUser.getLoginUser().setPassword(FacesTools.digestSHA256Hex(password1));
                }
            } else {
                FacesTools.addFlashMessage(FacesMessage.SEVERITY_ERROR, "Les mots de passe doivent être identique.");
                return;
            }
        }else{
            if(mailChanged){
                loginUser.setPassword(connectedUser.getLoginUser().getPassword());
            }
        }

        saveUserConnectedChange();
    }

    public void saveBusinessInfo(){
        if(connectedUser.getCAMax() == null || connectedUser.getTaxes() == null){
            FacesTools.addFlashMessage(FacesMessage.SEVERITY_ERROR, "Ces champs n'ont pas été renseigné correctement.");
            return;
        }

        if(connectedUser.getCAMax() == 0 || connectedUser.getTaxes() == 0){
            FacesTools.addFlashMessage(FacesMessage.SEVERITY_INFO, "Pour que votre tableau de bord soit précis, ces valeurs ne devraient pas être égal à zéro.");
        }

        saveUserConnectedChange();
    }

    public void savePersonnalInfo(){
        saveUserConnectedChange();
    }

    private void saveUserConnectedChange(){
        if(mailChanged){
            LoginUser current = loginUserDao.get(LoginUser.class, connectedUser.getLoginUser().getId());

            loginUser.setGroups(current.getGroups());
            groupDao.deleteUserGroupByUsername(current.getUserName());

            loginUserDao.insert(loginUser);
            connectedUser.setLoginUser(loginUser);
            userDAO.update(connectedUser);

            loginUserDao.delete(current);

            HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            httpSession.invalidate();
            FacesTools.redirect("/index.xhtml");
        }

        loginUserDao.update(connectedUser.getLoginUser());
        userDAO.update(connectedUser);

        FacesTools.addFlashMessage(FacesMessage.SEVERITY_INFO, "Vos informations ont été sauvegardé avec succès.");
    }

    /*****************************************************************************************
     * ---------------------------      ACCESSEURS      -------------------------------------*
     *****************************************************************************************/

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }

    public User getUser() {
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

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }
}
