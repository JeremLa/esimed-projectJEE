package controller;

import dao.ClientDao;
import dao.ProjectDao;
import dao.UserDAO;
import entity.Client;
import entity.Project;
import entity.User;
import entity.enumerable.ProjectStatus;
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
    private ProjectDao projectDao;
    @Inject
    private ClientDao clientDao;
    @Inject
    private UserDAO userDAO;
    private Project project;
    private ProjectStatus[] status;
    private List<Client> clients;
    private Boolean editMode;

    @PostConstruct
    public void init () {
        project = new Project();
        status = ProjectStatus.values();

        User currentUser = userDAO.findByUserName(FacesTools.currentUserName());
        clients = clientDao.getAllByUser(currentUser);
    }

    public void createProject(){
        projectDao.insert(project);

        FacesTools.addFlashMessage(FacesMessage.SEVERITY_INFO, "Projet créé avec succès.");
    }

    public void updateProject(){
        projectDao.update(project);
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
}
