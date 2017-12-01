package controller.chart;

import dao.ProjectDAO;
import entity.Project;
import entity.User;
import entity.enumerable.ProjectStatus;
import org.primefaces.model.chart.DonutChartModel;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ViewScoped
@Named
public class DonutChartController implements Serializable{
    @Inject
    private ProjectDAO projectDAO;
    @Inject
    private User user;
    private List<Project> projects;

    private DonutChartModel donutChartProject;

    @PostConstruct
    public void init() {
        projects = projectDAO.getAllByUser(user);
        createDonutProject();
    }

    private void createDonutProject(){
        donutChartProject = initDonutModel();
        donutChartProject.setTitle("Etat des projets");
        donutChartProject.setLegendPosition("w");
    }

    private DonutChartModel initDonutModel() {
        DonutChartModel model = new DonutChartModel();

        Map<String, Number> circle = new LinkedHashMap<>();

        for(ProjectStatus status : ProjectStatus.values()){
            circle.put(status.toString(), projects.stream().filter(p->status.toString().equals(p.getStatus().toString())).count());
        }

        model.addCircle(circle);

        return model;
    }

    public DonutChartModel getDonutChartProject() {
        return donutChartProject;
    }

    public void setDonutChartProject(DonutChartModel donutChartProject) {
        this.donutChartProject = donutChartProject;
    }
}
