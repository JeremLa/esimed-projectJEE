package controller;

import tools.FacesTools;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ApplicationScoped
@Named("app")
public class AppController implements Serializable{
    private String appName = "MEP";
    private String appFullName = "Micro Entreprise Project";
    private String title = "Connexion";

    public void logout() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        httpSession.invalidate();
        FacesTools.redirect("/index.xhtml");
    }

    public Boolean onClientListPage(){
        return "Liste clients".equals(title);
    }

    public String getAppName(){
        return appName;
    }

    public String getAppFullName(){
        return appFullName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
