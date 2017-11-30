package controller;

import dao.ClientDao;
import dao.ProjectDao;
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
    private ProjectDao projectDao;
    @Inject
    private ClientDao clientDao;
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
        clients = clientDao.getAllByUser(currentUser);

        projects = projectDao.getAll(Project.class);

        defaultValue = ProjectStatus.STARTED.toString();

        RequestContext.getCurrentInstance().execute("PF('projectTable').filter()");
    }

//    public void createProject(){
//        projectDao.insert(project);
//
//        this.project = new Project();
//
//        FacesTools.addFlashMessage(FacesMessage.SEVERITY_INFO, "Projet créé avec succès.");
//    }
//
//    public void updateProject(){
//
//        System.out.println("///////////////////////////////////////////// THIS.PROJECT :" + this.project);
//
//       try{
//           projectDao.update(project);
//
//           FacesTools.addFlashMessage(FacesMessage.SEVERITY_INFO, "Le projet a bien été mis à jour.");
//
//           RequestContext.getCurrentInstance().execute("PF('modalForUpdateProject').hide()");
//       }catch(Exception e){
//            e.printStackTrace();
//       }
//    }

    public void removeProject(Project project){
        projectDao.delete(project);

        this.project = new Project();

        FacesTools.addMessage(FacesMessage.SEVERITY_INFO, "Le projet a bien été supprimé.");
    }

    public Boolean haveBill(Project project){
        //TODO
        return true;
    }

//    public void setProjectForModal(Project project){
//
//        System.out.println("///////////////////////////////////////// set project for modal ///////////////////////////////////////");
//
//        this.project = project;
//        this.editMode = true;
//    }

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
