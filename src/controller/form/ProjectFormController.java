package controller.form;

import dao.ClientDAO;
import dao.ProjectDAO;
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
public class ProjectFormController implements Serializable{

    @Inject
    private ProjectDAO projectDAO;
    @Inject
    private ClientDAO clientDAO;
    @Inject
    private User user;

    private Project project = new Project();
    private Boolean editMode = false;
    private List<Client> clients;
    private ProjectStatus[] status;

    @PostConstruct
    public void init(){
        clients = clientDAO.getAllByUser(user);
        status = ProjectStatus.values();
    }

    public void save(){
        projectDAO.insert(project);

        this.project = new Project();

        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Projet créé avec succès.");
    }

    public void update(){
        projectDAO.update(project);
        editMode = false;

        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Le projet a bien été mis à jour.");
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        this.editMode = true;

        System.out.println(project);
    }

    public Boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public ProjectStatus[] getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus[] status) {
        this.status = status;
    }
}
