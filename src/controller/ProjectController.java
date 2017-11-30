package controller;

import dao.ClientDAO;
import dao.ProjectDAO;
import dao.UserDAO;
import entity.Client;
import entity.Project;
import entity.User;
import entity.enumerable.ProjectStatus;
import org.primefaces.context.RequestContext;
import tools.FacesTools;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class ProjectController implements Serializable{
    @Inject
    private ProjectDAO projectDAO;
    @Inject
    private ClientDAO clientDAO;
    @Inject
    private UserDAO userDAO;

    private Project project;
    private ProjectStatus[] status;
    private String defaultValue;
    private List<Client> clients;
    private List<Project> projects;
    private Boolean editMode;

    @PostConstruct
    public void init () {
        project = new Project();
        status = ProjectStatus.values();

        User currentUser = userDAO.findByUserName(FacesTools.currentUserName());
        clients = clientDAO.getAllByUser(currentUser);

        projects = projectDAO.getAll(Project.class);

        defaultValue = ProjectStatus.STARTED.toString();

        RequestContext.getCurrentInstance().execute("PF('projectTable').filter()");
    }

    public void removeProject(Project project){
        projectDAO.delete(project);

        this.project = new Project();

        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Le projet a bien été supprimé.");
    }

    public Boolean haveBill(Project project){
        //TODO
        return true;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProjectStatus[] getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus[] status) {
        this.status = status;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
