package controller.form;

import dao.ProjectDAO;
import entity.Project;
import tools.FacesTools;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class ProjectFormController implements Serializable{

    @Inject
    private ProjectDAO projectDAO;
    private Project project = new Project();
    private Boolean editMode = false;

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
    }

    public Boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }
}
